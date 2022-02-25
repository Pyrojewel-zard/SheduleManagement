# Serializable序列化的简要说明

**一、 持久化的简单介绍：**

　　“持久化”意味着对象的“生存时间”并不取决于程序是否正在执行——它存在或“生存”于程序的每一次调用之间。通过序列化一个对象，将其写入磁盘，以后在程序再次调用时重新恢复那个对象，就能圆满实现一种“持久”效果。

 

**二、 语言里增加了对象序列化的概念后，可提供对两种主要特性的支持：**

- 远程方法调用（RMI）使本来存在于其他机器的对象可以表现出好象就在本地机器上的行为。将消息发给远程对象时，需要通过对象序列化来传输参数和返回值。
- 使用一个Java Bean 时，它的状态信息通常在设计期间配置好。程序启动以后，这种状态信息必须保存下来，以便程序启动以后恢复；具体工作由对象序列化完成。

 

**三、 Serializable的一些说明：**

- 对象的序列化处理非常简单，只需对象实现了Serializable 接口即可（该接口仅是一个标记，没有方法）
- 序列化的对象包括基本数据类型，所有集合类以及其他许多东西，还有Class 对象
- 对象序列化不仅保存了对象的“全景图”，而且能追踪对象内包含的所有句柄并保存那些对象；接着又能对每个对象内包含的句柄进行追踪
- 使用transient关键字修饰的的变量，在序列化对象的过程中，该属性不会被序列化。

 

**四、 序列化的步骤：**

- 首先要创建某些OutputStream对象：OutputStream outputStream = new FileOutputStream("output.txt")
- 将其封装到ObjectOutputStream对象内：ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
- 此后只需调用writeObject()即可完成对象的序列化，并将其发送给OutputStream：objectOutputStream.writeObject(Object);
- 最后不要忘记关闭资源：objectOutputStream.close(), outputStream .close();

 

**五、 反序列化的步骤：**

- 首先要创建某些OutputStream对象：InputStream inputStream= new FileInputStream("output.txt")
- 将其封装到ObjectInputStream对象内：ObjectInputStream objectInputStream= new ObjectInputStream(inputStream);
- 此后只需调用readObject()即可完成对象的反序列化：objectInputStream.readObject();
- 最后不要忘记关闭资源：objectInputStream.close()，inputStream.close();

 

# Serializable序列化的代码实例

项目结构如下，源代码下载见[huhx友情链接](https://www.cnblogs.com/huhx/p/serializable.html#friend_link)：

![img](data:image/png;base64,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)

一、 首先我们建立一个Man类，实现了Serializable接口，用于Person类的测试：

[![复制代码](image/copycode.gif)](javascript:void(0);)

```
package com.huhx.model;
import java.io.Serializable;

public class Man implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public Man(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

[![复制代码](image/copycode.gif)](javascript:void(0);)

 

二、 我们再建立一个Person类，用于序列化：

[![复制代码](image/copycode.gif)](javascript:void(0);)

```
package com.huhx.model;
import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Man man;
    private String username;
    private transient int age;
    
    public Person() {
        System.out.println("person constru");
    }
    
    public Person(Man man, String username, int age) {
        this.man = man;
        this.username = username;
        this.age = age;
    }

    public Man getMan() {
        return man;
    }
    public void setMan(Man man) {
        this.man = man;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
```

[![复制代码](image/copycode.gif)](javascript:void(0);)

 

 三、 编写一个包含main方法的测试类：MainTest，它的writeSerializableObject用于序列化对象：

[![复制代码](image/copycode.gif)](javascript:void(0);)

```
// Serializable：把对象序列化
public static void writeSerializableObject() {
    try {
        Man man = new Man("huhx", "123456");
        Person person = new Person(man, "刘力", 21);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("output.txt"));
        objectOutputStream.writeObject("string");
        objectOutputStream.writeObject(person);
        objectOutputStream.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

[![复制代码](image/copycode.gif)](javascript:void(0);)

 

四、  测试类MainTest，它的readSerializableObject用于反序列化对象：

[![复制代码](image/copycode.gif)](javascript:void(0);)

```
// Serializable：反序列化对象
public static void readSerializableObject() {
    try {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("output.txt"));
        String string = (String) objectInputStream.readObject();
        Person person = (Person) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println(string + ", age: " + person.getAge() + ", man username: " + person.getMan().getUsername());
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

[![复制代码](image/copycode.gif)](javascript:void(0);)

 

 五、 在Main方法添加以上两个方法的运行，结果如下：

　　 ![img](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAATcAAAAQCAIAAAD8nq9fAAACn0lEQVRoge1a27HFIAhMXRZkPVZjMxbj/fAFBojmmJiZy36diQZhYSM654gKheLbOHY7oFAoLqAqVSi+DlWpQvF1PKJSbw/jwhOWP4jgzJFg/W5fFDGWjIxnY3b++7hWqTNf9n83vD2Mqz+VqY8gTFbt7PyX0VRat4TDGJMqr20T/W6RBowLZUoeqW/AvTR/q6w9mYkx+vLUOGe7FydQzfQWGPswtCIz/MpQ1rwFq91LdfLEmLxo9rcYouIS+WRXSJOSPUgRkffI8kPmXfLn7L8c76K8BGes65scgQdyfoRBZj/39IhH9aeu722XGIaXEmj6DSdBa/UJJKuOOoMovM+B93X9ZpO3Dx0MzuCC+EGlfWGNobLjba37vDwTF8ensEKLBzjN5V3ih8o76w/pPx/vqrzAbEN/BB7I+dmJgCN8HXUvBR897IygUq44SJUS7OCyHqk2Fnjbh/VC2D/1CLf5X6bSUvTJkUYXGRdfbcIKzHwq7zI/1HKsfTkvXbzr8iKoceo5CGHnTQt1LgVnrfiiSkeqjQG6rkIlTtv39p6cqIVbXLcPN6xKmbhWqhQHk2kR+ZlQ6VVezvEuystKlZbv2MaDa1YpkhW+BmlDpT+p035VKV639BbAjUFuoBpxZ8LZF2+hJzqrGJwFNtE7N/w/7y1MXDdUCvsIkn8YgMTPuEo5//neYVVerjt8zIPYCbeT7K79tKqUbzP8+VYAtklwoH8ObxcOdAonbnGMQdr2M3dJ0Iy14GKdt8+HPKNSaIegbcB/yE55p95zkHFJfF4ThG7RhLxTQ3TeBX8I/50U75K8jNVb44Gbf4fnZ/ChfzV0O3C/ta62/zSW+6/4t9ivUvC1BWeSAG80H7D/NNb5r1DsV6lCoZChKlUovo4/sqKBsijdi+4AAAAASUVORK5CYII=)

-  在Person类中包含Man的引用，当Person被序列化的时候，从结果可以知道Man也被序列化了
- writeObject方法可以传入String，是因为String首先是一个类，其次它也是实现了Serializable接口的
- Person类中的age字段是transient，从打印结果可以看到，序列化Person person = new Person(man, "刘力", 21)对象时，age没有进行序列化。如果transient修饰的Object类型的，那么打印的结果将会是null

 

# Externalizable序列化的代码实例

首先我们看一下Externalizable的定义：继承了Serializable接口

```
public interface Externalizable extends java.io.Serializable
```

一、 同样的我们先创建一个实现了Externalizable的User类：重写里面两个方法readExternal和writeExternal

[![复制代码](image/copycode.gif)](javascript:void(0);)

```
package com.huhx.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class User implements Externalizable {
    private String user;

    public String getUser() {
        return user;
    }

    public int getAge() {
        return age;
    }

    private int age;

    public User() {
        System.out.println("user constructor.");
    }

    public User(String user, int age) {
        System.out.println("user constructor two.");
        this.user = user;
        this.age = age;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("read external.");
        user = (String) in.readObject();
        age = in.readInt();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("write external.");
        out.writeObject(user);
        out.writeInt(age);
    }
}
```

[![复制代码](image/copycode.gif)](javascript:void(0);)

 

二、 在MainTest中加入方法writeExternalizableObject，用于序列化对象User

[![复制代码](image/copycode.gif)](javascript:void(0);)

```
// Externalizable的序列化对象
public static void writeExternalizableObject() {
    User user = new User("huhx", 22);
    try {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Externalizable.txt"));
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

[![复制代码](image/copycode.gif)](javascript:void(0);)

 

三、 在MainTest中加入方法writeExternalizableObject，用于反序列化对象User

[![复制代码](image/copycode.gif)](javascript:void(0);)

```
// Externalizable的反序列化对象
public static void readExternalizableObject() {
    try {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Externalizable.txt"));
        User user = (User) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println("name: " + user.getUser() + ", age: " + user.getAge());
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

[![复制代码](image/copycode.gif)](javascript:void(0);)

 

四、 在Main方法添加以上两个方法的运行，结果如下：

　　![img](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQwAAABTCAIAAADlQa5SAAAFjklEQVR4nO2d0YGrIBBFrYuCqIdqaMZi3A+NDmSGkWgS15zz9Z5BmEUuYsJ1hgkAmgzfDuATpBAz9cOrLCIZUxiGYb4UOQ7DMIQ0roVSGBZCCCFN8pzleKoKhzQ+Pvcv8NzifFoKW8uigaWSJc4Yq+NmnDLI8gQ1znY/KHHa9avxd/ePUX916hLY4yqo7cLLbHeSUc5XOa6DYxTDNkd5JbbxM6YgdbKOr/nf7QuV43YpxxTWYZmCGKA5rvWPKUjNuHFOjZlYi9PqBytOq34rfqvdBkr9Y31sjbTVLryELxI5gVbDxJxDi9Pb5KheRXsQ9MU5TVNTJM9xGvUbcVr12/Fb7TYwRBjzfNMIaQuv3S68xB6RCLaZqTVoviQSNc5puqlIcgxpzDGkHEPKjyKI5A0UItmGoVhMyGXVlKNcB5vd33Nlynq2RU3RrhijlkisOIuP5JrJiNPqBytOq34rfqvdBnr8OcYYQxqnHEMISjDP7S43Wx5U+hDfbm0rqJBSXJ9Zy3WV8aC8fSTXPHsviKxnK1+u6NaJcvvvoyk3ThHUetyO0+gHM069fj3+1/rHrn8OrdSv3q6sCJH08RNfAQMcAZEAOCASAAdEAuCASAAcEAmAAyIBcEAkAA6IBMDhqEham1N+g/UH7h/vhxvzljvJt0xCXzQnjeWWKbgTi0gaJh7LJKTPoA0TkmHSMlHLyyDWKNvmpx5zWMvUJXZdtbZFwu143Em8rd3bCCtNQurgMPwPtklLwyy/buyT9qvX2jVMV6qpa8pZugurlhDJjdmWW7qJZ8be2r1XJG2Tllpvo/y43gaOtdvhJ2nvrkUkd2YTiW7iEZ+p5+++kzRNWgqOqUsdqd3tdpmuhkIviOR3EA/uholn/qhTJIpJqPd7MKu8WA7NVnb5worOdveLZEzVGzD2iQT/xh2oTFfPJh7LJFQfN55xXZNWi+fyDdNVZ7v639WoX6y2QozrS1Wa/YBIbgE/JgI4IBIAB0QC4IBIABwQCYADIgFwQCQADogEwAGRADggEgCHnxAJmajgCGS6unamK7gAZLr6B5mu4LuQ6eofJPGB70KmK0QCDmS6ulSmK/wnV4RMV412P5/pCpFckZ/4ChjgCIgEwAGRADggEgAHRALggEgAHBAJgAMiAXBAJAAObxVJnn+vfmcTn4OMVj/Lu+8kvS+T3wUZreCTyPwkyzRZm4GsDFVW5idRPuYdIiGjFVyb8k6imYFMc5We+enpjfRktIJ/Ti2SegQ0zFXqLteqBpnWQ4WMVnB5PJGYDxVG5qdekZDRCi6PKxLDXGVmfirsTpXXXIWMVnBxVpG0zECqn8nI/FRZzeO+x5K6fjJawXXgx0QAB0QC4IBIABwQCYADIgFwQCQADogEwAGRADggEgCHi4pE5gl5R/n/hXjv6rjnOJzLRUUyaZsLzy3/XxB70oq3dFvH4XTKTFc7TUhz6RCWDUtLAc+ktdW164KOKcS0VlVmutIyUanlTfPWC5hmrPWD8gXbJ/WDxJoI7jpBXIQi9UKHCWkt/ZjF1uvUMj91ikSOt0IMRkYutXzDvNWHYcaq+kRNBXGkH4qzLNvCXV4kcE12JPFRTUjrlvLH1vjl9Lb5qYeGGLqOiz/h2NK93Q/b/55uI8f6QbauVHFQ+bADVySGCckSyXmz2pkiMcxbPXj9ULd75uxepWh0j8O5eCKxTEimSJrmp95nEkMMaiaq5jJMN291hGObsap0wUZmrIqOfrCMa3sMbXAKxYN7+YBrm5CSKL287GGUj9R20qm9g6NlutIyUVnl2+atHIf9M7FpMpMfhMK3eLwfzLfDNN4aA2dz3a+AP0CRRfoMcMDfkh8Wyfj8wqAXEbcS1j835IdFArAPRALggEgAHBAJgAMiAXD4A/DfhRmr35NkAAAAAElFTkSuQmCC)

- 首先User user = new User("huhx", 22);执行了User的含参构造函数
- 当执行到writeObject(user);方法时，由于User实现了Externalizable接口，所以它的writeExternal会执行，
- 在User中的readExternal方法中调用了ObjectInput的readObject方法，在这个方法中通过反射机制创建User的实例，调用了User的无参构造函数。
- 然后在readObject方法执行的时候，同样会先执行User类的readExternal方法。这个会在后续源代码分析时讲到



# Java序列化的原理分析

[java基础---->Serializable的使用](http://www.cnblogs.com/huhx/p/serializable.html)的代码的演示，我们可以知道：

- 调用writeObject方法序列化一个对象，是将其写入磁盘，以后在程序再次调用readObject时，根据wirteObject方法磁盘的文件重新恢复那个对象
- Externalizable 接口扩展了Serializable，并增添了两个方法：writeExternal()和readExternal()。在序列化和重新装配的过程中，会自动调用这两个方法

## Java的序列化

一、 objectOutputStream.writeObject(user)方法执行的详细如下：

- ObjectOutputStream的构造函数设置enableOverride = false;

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

```
public ObjectOutputStream(OutputStream out) throws IOException {
    verifySubclass();
    bout = new BlockDataOutputStream(out);
    handles = new HandleTable(10, (float) 3.00);
    subs = new ReplaceTable(10, (float) 3.00);
    enableOverride = false;
    writeStreamHeader();
    bout.setBlockDataMode(true);
    if (extendedDebugInfo) {
        debugInfoStack = new DebugTraceInfoStack();
    } else {
        debugInfoStack = null;
    }
}
```

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

- 所以writeObject方法执行的是writeObject0(obj, false);

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

```
 public final void writeObject(Object obj) throws IOException {
    if (enableOverride) {
        writeObjectOverride(obj);
        return;
    }
    try {
        writeObject0(obj, false);
    } catch (IOException ex) {
        if (depth == 0) {
            writeFatalException(ex);
        }
        throw ex;
    }
}
```

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

 

 二、 在writeObject0方法中，以下贴出重要的代码

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

```
if (obj instanceof String) {
    writeString((String) obj, unshared);
} else if (cl.isArray()) {
    writeArray(obj, desc, unshared);
} else if (obj instanceof Enum) {
    writeEnum((Enum) obj, desc, unshared);
} else if (obj instanceof Serializable) {
    writeOrdinaryObject(obj, desc, unshared);
} else {
    if (extendedDebugInfo) {
        throw new NotSerializableException(
            cl.getName() + "\n" + debugInfoStack.toString());
    } else {
        throw new NotSerializableException(cl.getName());
    }
}
```

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

- 从上可以看出，如果对象没有实现Serializable接口，在序列化的时候会抛出NotSerializableException异常
- 上述Person是一个类对象，所以会执行writeOrdinaryObject(obj, desc, unshared)方法;

 

三、 在writeOrdinaryObject(obj, desc, unshared)方法中，重要代码如下：

```
if (desc.isExternalizable() && !desc.isProxy()) {
    writeExternalData((Externalizable) obj);
} else {
    writeSerialData(obj, desc);
}
```

- 如果对象实现了Externalizable接口，那么执行writeExternalData((Externalizable) obj)方法
- 如果对象实现的是Serializable接口，那么执行的是writeSerialData(obj, desc);

 

四， 我们首先看一下writeSerialData方法，主要执行方法：defaultWriteFields(obj, slotDesc);

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

```
private void defaultWriteFields(Object obj, ObjectStreamClass desc) throws IOException {
    Class<?> cl = desc.forClass();
    if (cl != null && obj != null && !cl.isInstance(obj)) {
        throw new ClassCastException();
    }

    desc.checkDefaultSerialize();

    int primDataSize = desc.getPrimDataSize();
    if (primVals == null || primVals.length < primDataSize) {
        primVals = new byte[primDataSize];
    }
    desc.getPrimFieldValues(obj, primVals);
    bout.write(primVals, 0, primDataSize, false);

    ObjectStreamField[] fields = desc.getFields(false);
    Object[] objVals = new Object[desc.getNumObjFields()];
    int numPrimFields = fields.length - objVals.length;
    desc.getObjFieldValues(obj, objVals);
    for (int i = 0; i < objVals.length; i++) {
        if (extendedDebugInfo) {
            debugInfoStack.push(
                "field (class \"" + desc.getName() + "\", name: \"" +
                fields[numPrimFields + i].getName() + "\", type: \"" +
                fields[numPrimFields + i].getType() + "\")");
        }
        try {
            writeObject0(objVals[i],
                         fields[numPrimFields + i].isUnshared());
        } finally {
            if (extendedDebugInfo) {
                debugInfoStack.pop();
            }
        }
    }
}
```

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

- 在此方法中，是系统默认的写入对象的非transient部分。我们在后续的自定义序列化中会做讲解。

 

五、 我们再来看一下writeExternalData的方法，重要代码如下：

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

```
try {
    curContext = null;
    if (protocol == PROTOCOL_VERSION_1) {
        obj.writeExternal(this);
    } else {
        bout.setBlockDataMode(true);
        obj.writeExternal(this);
        bout.setBlockDataMode(false);
        bout.writeByte(TC_ENDBLOCKDATA);
    }
}
```

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

- 在此方法中obj.writeExternal(this)执行了序列化对象的writeExternal方法，在上述例子中也就是User类的writeExternal方法。

 

## Java的反序列化

一、 与上述的write过程类似，objectInputStream.readObject()方法执行了readObject0(false)方法：主要代码：

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

```
switch (tc) {
    case TC_NULL:
        return readNull();

    case TC_REFERENCE:
        return readHandle(unshared);

    case TC_CLASS:
        return readClass(unshared);

    case TC_CLASSDESC:
    case TC_PROXYCLASSDESC:
        return readClassDesc(unshared);

    case TC_STRING:
    case TC_LONGSTRING:
        return checkResolve(readString(unshared));

    case TC_ARRAY:
        return checkResolve(readArray(unshared));

    case TC_ENUM:
        return checkResolve(readEnum(unshared));

    case TC_OBJECT:
        return checkResolve(readOrdinaryObject(unshared));

    case TC_EXCEPTION:
        IOException ex = readFatalException();
        throw new WriteAbortedException("writing aborted", ex);

    case TC_BLOCKDATA:
    case TC_BLOCKDATALONG:
        if (oldMode) {
            bin.setBlockDataMode(true);
            bin.peek();             // force header read
            throw new OptionalDataException(
                bin.currentBlockRemaining());
        } else {
            throw new StreamCorruptedException(
                "unexpected block data");
        }

    case TC_ENDBLOCKDATA:
        if (oldMode) {
            throw new OptionalDataException(true);
        } else {
            throw new StreamCorruptedException(
                "unexpected end of block data");
        }

    default:
        throw new StreamCorruptedException(
            String.format("invalid type code: %02X", tc));
}
```

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

- 根据不同的对象类型做相应的处理，这里我们关注的是TC_OBJECT，执行的方法是：checkResolve(readOrdinaryObject(unshared));

 

七、 跟进去，我们可以看到在readOrdinaryObject(unshared)中，执行了以下代码：

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

```
private Object readOrdinaryObject(boolean unshared) throws IOException {
    if (bin.readByte() != TC_OBJECT) {
        throw new InternalError();
    }

    ObjectStreamClass desc = readClassDesc(false);
    desc.checkDeserialize();

    Class<?> cl = desc.forClass();
    if (cl == String.class || cl == Class.class
            || cl == ObjectStreamClass.class) {
        throw new InvalidClassException("invalid class descriptor");
    }

    Object obj;
    try {
        obj = desc.isInstantiable() ? desc.newInstance() : null;
    } catch (Exception ex) {
        throw (IOException) new InvalidClassException(
            desc.forClass().getName(),
            "unable to create instance").initCause(ex);
    }

    passHandle = handles.assign(unshared ? unsharedMarker : obj);
    ClassNotFoundException resolveEx = desc.getResolveException();
    if (resolveEx != null) {
        handles.markException(passHandle, resolveEx);
    }

    if (desc.isExternalizable()) {
        readExternalData((Externalizable) obj, desc);
    } else {
        readSerialData(obj, desc);
    }

    handles.finish(passHandle);

    if (obj != null &&
        handles.lookupException(passHandle) == null &&
        desc.hasReadResolveMethod())
    {
        Object rep = desc.invokeReadResolve(obj);
        if (unshared && rep.getClass().isArray()) {
            rep = cloneArray(rep);
        }
        if (rep != obj) {
            handles.setObject(passHandle, obj = rep);
        }
    }

    return obj;
}
```

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

- 加载序列化对象的Class，如果可以实例化，那么创建它的实例：desc.newInstance()，User类的无参构造方法执行

 

# 自定义Serializable的使用

一、 我们写了Human类，为了方便Main方法也在这里类里面。

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

```
package com.huhx.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Human implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String username;
    private transient String password;
    
    public Human(String username, String password) {
        this.username = "not transient " + username;
        this.password = "transient " + password;
    }
    
    @Override
    public String toString() {
        return username + "\n" + password;
    }
    
    private void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
        inputStream.defaultReadObject();
        password = (String) inputStream.readObject();
    }
    
    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(password);
    }
    
    public static void main(String[] args) throws Exception {
        Human human = new Human("huhx", "liuli");
        System.out.println("before: \n" + human);
        
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(buff);
        objectOutputStream.writeObject(human);
        
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buff.toByteArray()));
        Human human2 = (Human) objectInputStream.readObject();
        System.out.println("after: \n" + human2);
    }
}
```

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

- 定义了两种方法：readObject和writeObject方法，注意命名是有限制的，这个等下解释。好了，我们先看结果

　　![img](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAQYAAABoCAIAAACc3sn1AAAFmUlEQVR4nO3d24GqMBSFYeqiIOqhGpqhGOZBLnuF7Jg4gBH+7+kcxJBRlgHNhmYCYDTf7gBQFyIBiDkSQ9c0TduPxc8fumbVDQd3DrjeNkr0bXkkxr5t+2M7BHyXRmLo2/kT38RjXBc2jQmAHR+Ch8wTtpHjtbDtx6W95SGnfbMRhh9cx0bCJGHs22VHtKPH2Ley18ZGib41eRo6Wf+1h7+WDN1rA6n2iQQuJ5Gwu97Yt92gH+H7U4Z9JOanbYbODDjyn2X9RPvA5dxziWXfHrrE2cL/I5FuH7icHjite6c5cBo6/6uo+IGTXV33+Egkku1z4ITLyZewXbcexcheqgc36zmGc7yjx0LL4vBs3O7m0fbNs4gErsNPdYAgEoAgEoAgEoAgEoAgEoAgEoAgEoAgEoB4SiSS00aO9/oFP/9X99L1cZ5/RSKc4nf0+rUp6v9uAuTB6+MkcyTmT6mui000ipUE7SZ1v3k7/fXd0iIzK2r9gM/tZ9uu8xHXLafmpevKkfZL/97XLt6vz5pXt6PBUjwyJtaf7IsyvyBXjnZPtI0SY9/ad2594RMlQYeNErHSomkYbEWeziqM9NP+e+jCAr1xV0frlS557Zf+veNravEY6efWipka7K0/d27UnuE0EonIW5WsfzgwEpGPvviMWneXSl8aIYyEX7rkt/+PAyfd9YuWm94yPlyh2kjI+bDtRWrX0WfbBbtRwi1dqjESS9gZIi7wLhLJkqDtoaHLeb/c9aMFqFLP9H6UkH4OXdCd/YGT9x1UMhIFf29i198+boJzCfeAajv7YKQ4m5xe62lcY9+5+BHJED/NdUXWd0uLzGbn2qZuSPXTqUMK299fNMQuT70OJX9v3uvZ9v18/Sxv/Tf9wQme8rsEkIlIAIJIAIJIAIJIAIJIAIJIAIJIAIJIAKKWSFxc4jMlJo0X9ueodlAJicSvF7F80P/93KfPHNUOvm6JxPNKfNbnBduN9idR+lPUDuqXN0rcscTH2663PD0pPb8dVC43Evcr8Ylv119OJB7i40j8fImPs113OZF4iCASDyrx8bbrLfdKf0rbQeX0S9jHlPj42/X7Eyv9+aQd1K2W3yWAShAJQBAJQBAJQBAJQBAJQBAJQBAJQBAJQORGIjVlD7iRvEjYGU3Gr5ccAXsmErGSoGk/WeeVjUTJTqz0Z0qUIm0bIWD4PhuJeEnQNJWNEl7pzzQ5pUjbA0QC32cikbhofn4k/NKfafLLJ4BqrJFwS4Lm/+eOEm7pz/IgkUDVzOUInJKg8FEjWrKTukyLGwkOnFCL4I6m80nxWhI0hXU8sbPi3d4cqxZyS5FMQ0QC38dPdYAgEoAgEoAgEoAgEoAgEoAgEoAgEoAgEoB4SiQuvhuQvR/FGevjPP+KRGkJ0a+XHBX1P5w6efT6OMkcidy7B31815/n3eVo7NuuX5+13RwjmPVl/7T9+pN9UeYXhMnE5wqm/UXu7tO3emluKZQ7aJS4412O7CX3g35GbwbgrT93bozNUMYJJBKRt2o3nNv53QdG4n53OUrs+kXLTW8ZH65QbSR+/i5HR0ZiCTtDxAXeRSK4e5DuSaV3/XnUXY4Su370bkbJA6rt7IOR4mxyeq2ncY195+JHJAV3/fHWv+ddjvJez+1uRt76b/qDEzzldwkgE5EABJEABJEABJEABJEABJEABJEABJEARC2RuLjEZ0pMGi/sz1HtoBISiV8vYvmg//u5T585qh18nblyuDMT6K4lPuvzgu1G+5Mo/SlqB/XLGyXuWOLjbddbnp6Unt8OKpcbifuV+MS36y8nEg/xcSR+vsTH2a67nEg8RBCJB5X4eNv1lnulP6XtoHL6JexjSnz87fr9iZX+fNIO6lbL7xJAJYgEIIgEIIgEIIgEIIgEIIgEIIgEIP4AC8V/FpAthR0AAAAASUVORK5CYII=)

- 一个String 保持原始状态，其他设为transient（临时），以便证明非临时字段会被defaultWriteObject()方法自动保存，而transient 字段必须在程序中明确保存和恢复。字段是在构建器内部初始化的，而不是在定义的时候，这证明了它们不会在重新装配的时候被某些自动化机制初始化。若准备通过默认机制写入对象的非transient 部分，那么必须调用defaultWriteObject()，令其作为writeObject()中的第一个操作；并调用defaultReadObject()，令其作为readObject()的第一个操作

 

二、 Human类中的readObject和writeObject方法的执行，不是我们调用的，而是ObjectInputStream与ObjectOutputStream 对象的readObject，writeObject方法去调用的。重要源代码如下

-  ObjectStreamClass(final Class<?> cl) 构造方法：

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

```
if (externalizable) {
    cons = getExternalizableConstructor(cl);
} else {
    cons = getSerializableConstructor(cl);
    writeObjectMethod = getPrivateMethod(cl, "writeObject",
        new Class<?>[] { ObjectOutputStream.class },
        Void.TYPE);
    readObjectMethod = getPrivateMethod(cl, "readObject",
        new Class<?>[] { ObjectInputStream.class },
        Void.TYPE);
    readObjectNoDataMethod = getPrivateMethod(
        cl, "readObjectNoData", null, Void.TYPE);
    hasWriteObjectData = (writeObjectMethod != null);
}
```

[![复制代码](image/copycode-16376013711942.gif)](javascript:void(0);)

- 在[writeSerialData的方法](https://www.cnblogs.com/huhx/p/sSerializableTheory.html#writeSerialData_externalData)当中，有以下的代码：利用反射机制去执行方法

```；【【【【【【【【【【【【‘【【【【-
slotDesc.invokeWriteObject(obj, this);
```
























