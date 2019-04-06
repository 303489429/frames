## 内网调用 采用thrift
## 编译并生成客服端服务端代码 thrift --gen java data.thrift
## python 的thrift 第三方依赖安装，可以安装到本地电脑中。
## 安装命令：sudo python setup.py install
## 生成的依赖位于：/Library/Python/2.7/site-packages/six-1.12.0-py2.7.egg

namespace java com.shengsiyuan.thrift
namespace py py.shengsiyuan.thrift

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct Person {
    1: optional String username ,
    2: optional int age ,
    3: optional boolean married
}

exception DataException {
    1: optional String message ,
    2: optional String callStack ,
    3: optional String date
}

service PersonService {

    Person getPersonByUserName(1: required String username) throws (1: DataException dataException) ,
    void savePerson(1: required Person person) throws (1:DataException dataException)
}
