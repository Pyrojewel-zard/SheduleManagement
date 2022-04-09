```java
Button btn;
    EditText txInput,txOutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button);
        txInput=(EditText) findViewById(R.id.txtinput);
        txOutput=(EditText) findViewById(R.id.txtoutput);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input=txInput.getText().toString();
                System.out.println(input);
                if(input!=""){
                    //这里是把第一个input的默认值设为了空
                txOutput.setText(deal(input));
            }else {
                    Toast t = Toast.makeText(MainActivity.this, "没有输入内容", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });

    }
    public String deal(String str){
        File file=new File("libs/TimeExp.m");
        TimeNormalizer normalizer = new TimeNormalizer(file.toURI().toString());
        normalizer.parse(str);// 抽取时间
        TimeUnit[] unit = normalizer.getTimeUnit();
        String a=str;
        if(unit.length!=0) {
            a = stringPreHandlingModule.numberTranslator(a).replace(unit[0].Time_Expression, "");
            //删除掉原本内容中的时间部分，余下的作为task
            return DateUtil.formatDateDefault(unit[0].getTime())+a;
            //方便显示我就又加在一起了，只要其中一项，或者两项分开，改一下就好
        }else{
            Toast t = Toast.makeText(MainActivity.this, "没有识别到时间关键词", Toast.LENGTH_LONG);
            t.show();
            return "";
        }
    }
```
还没想到怎么加进去，毕竟还有地方没搞好

卡了很久的是在TimeNormalizer，关于pattern的初始化那边

使用的时候，也就是说，直接就复制粘贴deal函数，把要改的细节改改就好