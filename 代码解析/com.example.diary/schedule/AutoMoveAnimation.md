```java
package com.example.diary.schedule;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

public class AutoMoveAnimation extends Animation {
    private final View mView;
    private final int mDistance;
    private final float mPositionY;

    public AutoMoveAnimation(View view, int distance) {
        mView = view;
        mDistance = distance;
        setDuration(200);
        setInterpolator(new DecelerateInterpolator(1.5f));
        mPositionY = mView.getY();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        mView.setY(mPositionY + interpolatedTime * mDistance);
    }
}
```

