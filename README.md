# ChatRoom 聊天室 

> 使用Java编写的有屏蔽指定信息功能的local host简易聊天室

![License](https://img.shields.io/badge/license-MIT-green.svg)

本项目是基于Java Networking Class(Socket/ServerSocket) I/O，动态储存，以及多线程编写的简单聊天室

## 内容列表

- [Getting Started 使用指南](#GettingStarted使用指南)

- [Release History 版本历史](#ReleaseHistory版本历史)

- [Author 关于作者](#Author关于作者)

- [License 授权协议](#License授权协议)


## GettingStarted使用指南

首先请运行 ChatServer.java，您有以下两种方式建立聊天室：

1. 使用默认port number

port number 默认为1500（可于ChatServer.java中main class 修改默认portnumber）

```
> java ChatServer
```

2. 自选 port number

若选择其他port number 请输入您的port number作为编译argument（更改portNUmber 为您的port number） 

```
> java ChatServer portNumber
```

至此，聊天室已建立完成，可通过运行 ChatClient.java 添加用户至聊天室。
您有以下四种方式添加用户：

1. 若您希望以匿名者的身份加入默认portnumber聊天室：
```
> java ChatClient
```

2. 若您希望以您输入的昵称（更改username为您的昵称）加入默认portnumber聊天室：
```
> java ChatClient username
```

3. 若您希望以您输入的昵称（更改username为您的昵称）加入指定portnumber聊天室（更改portnumber为您希望加入的聊天室的portnumber）：
```
> java ChatClient username portNumber
```

4. 若您希望以您输入的昵称（更改username为您的昵称）加入指定聊天室（更改portnumber以及serverAddress为您希望加入的聊天室的portnumber和serveraddress）：
```
> java ChatClient username portNumber serverAddress
```

现在您可以在聊天室与其他用户对话了！

此聊天室还会屏蔽指定信息，若用户发出此信息，server会自动使用“*”代替该信息；
您可以在 [badwords.txt](ChatRoom/badwords.txt) 添加/删除需要屏蔽的信息。

## ReleaseHistory版本历史

* 0.1.0

## Author关于作者

* **BX** - *Initial work* - [BX](https://github.com/BX126)

## License授权协议

[MIT LICENSE](LICENSE) @ BX