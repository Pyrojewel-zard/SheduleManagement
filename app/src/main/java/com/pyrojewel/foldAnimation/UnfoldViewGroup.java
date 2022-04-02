package com.pyrojewel.foldAnimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @author Pyrojewel
 * 见[csdn](https://blog.csdn.net/q1242027878/article/details/54091904)
 * 我也是找来的……
 */
public class UnfoldViewGroup extends ViewGroup {
    /**旋转动画*/
    private static final int DO_ROTATE = 1;
    /**恢复旋转之前的状态*/
    private static final int RECOVER_ROTATE = -1;
    /**菜单展开状态*/
    private static final int UNFOLDING = 2;
    /**菜单折叠状态*/
    private static final int FOLDING = 3;
    /**viewGroup的宽*/
    private int mWidth=400;
    /**ViewGroup的高*/
    private int mHeight=620;
    /**子view展开的距离*/
    private int length =200;
    /**菜单展开与折叠的状态*/
    private int flag = FOLDING ;
    /**展开之后的缩放比例*/
    private float mScale = 0.8f;
    /**动画时长*/
    private int mDuration=400;
    /**在Activity中显示的button*/
    private View mainButton;

    public UnfoldViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量子view的宽高 这是必不可少的  不然子view会没有宽高
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //设置该viewGroup的宽高
        setMeasuredDimension(mWidth,mHeight);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int cCount = getChildCount();
        if(cCount==0){
            //没有子view  直接返回就好
            return;
        }
        //第一个子view当做显示的button
        mainButton = getChildAt(0);
        //获取宽高
        int width = mainButton.getMeasuredWidth();
        int height = mainButton.getMeasuredHeight();
        //1：相对于父布局  控件的left
        //2：控件的top
        //3：右边缘的left
        //4：底部的top
        //所以后两个直接用left加上宽 以及 top加上height就好
        mainButton.layout(mWidth-width,(mHeight-height)/2,mWidth,(mHeight-height)/2+height);

        //设置子view的初始位置  与mainButton重合  并且设置为不可见
        for(int i=1;i<cCount;i++) {
            final View view = getChildAt(i);
            view.layout(mWidth - width, (mHeight - height) / 2, mWidth, (mHeight - height) / 2 + height);
            view.setVisibility(INVISIBLE);
        }


        //设置主按钮的点击事件
        setMainButtonListener(mainButton);
        //设置子view的点击事件
        setChildrenListener();
    }
    /**
     * 设置主按钮的点击事件
     * @param mainButton
     */
    private void setMainButtonListener(final View mainButton) {

        //得到子view的个数
        final int cCount = getChildCount();
        mainButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==FOLDING) {//折叠状态
                    for (int i = 1; i < cCount; i++) {
                        View view = getChildAt(i);
                        view.setVisibility(VISIBLE);
                        //开始平移  第一个参数是view 第二个是角度
                        setTranslation(view, 180 / (cCount - 2) * (i - 1));
                    }
                    flag = UNFOLDING;//展开状态
                    //开始旋转
                    setRotateAnimation(mainButton,DO_ROTATE);

                }else {
                    setBackTranslation();
                    flag =FOLDING;
                    //开始反向旋转 恢复原来的样子
                    setRotateAnimation(mainButton,RECOVER_ROTATE);
                }
            }
        });

    }
    public void setTranslation(View view,int angle){
        int x  = (int) (length*Math.sin(Math.toRadians(angle)));
        int y = (int) (length*Math.cos(Math.toRadians(angle)));
        Log.d("ICE","angle"+angle +"y:"+y);
        ObjectAnimator tX = ObjectAnimator.ofFloat(view,"translationX",-x);
        ObjectAnimator tY = ObjectAnimator.ofFloat(view,"translationY",-y);
        ObjectAnimator alpha  = ObjectAnimator.ofFloat(view,"alpha",1);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX",mScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,"scaleY",mScale);

        AnimatorSet set = new AnimatorSet();
        set.play(tX).with(tY).with(alpha);
        set.play(scaleX).with(scaleY).with(tX);
        set.setDuration(mDuration);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }
    private void setBackTranslation(){
        int cCount =getChildCount();
        for (int i = 1; i < cCount; i++) {
            final View view = getChildAt(i);
            ObjectAnimator tX = ObjectAnimator.ofFloat(view,"translationX",0);
            ObjectAnimator tY = ObjectAnimator.ofFloat(view,"translationY",0);
            ObjectAnimator alpha  = ObjectAnimator.ofFloat(view,"alpha",0);
            //透明度 0为完全透明
            AnimatorSet set = new AnimatorSet();
            //动画集合
            set.play(tX).with(tY).with(alpha);
            set.setDuration(mDuration);
            //持续时间
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.start();
            //动画完成后 设置为不可见
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(INVISIBLE);
                }
            });
        }

    }
    /**缩放动画*/
    public void setRotateAnimation(View view,int flag){
        ObjectAnimator rotate = null;
        if(flag==DO_ROTATE) {
            rotate = ObjectAnimator.ofFloat(view,"rotation",135);
        } else {
            rotate = ObjectAnimator.ofFloat(view,"rotation",0);
        }
        rotate.setDuration(mDuration);
        rotate.start();
    }
    private void setChildrenListener() {
        final int cCount = getChildCount();
        for(int i=1;i<cCount;i++){
            final View view = getChildAt(i);
            //设置点击时候执行点击事件并且缩回原来的位置
            view.setOnTouchListener(new OnTouchListener() {
                int x,y;
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            x = (int) event.getX();
                            y = (int) event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            if((int)event.getX() == x && (int)event.getY()==y){
                                //如果手指点击时 与抬起时的x y 坐标相等 那么我们认为手指点了该view
                                setBackTranslation();  //折叠菜单
                                setRotateAnimation(mainButton,RECOVER_ROTATE); //旋转mainButton
                                flag = UNFOLDING;//设置为展开状态
                                //执行该view的点击事件
                                view.callOnClick();
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
        }
    }
}