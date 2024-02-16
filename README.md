![unidbg-qd-sign](https://socialify.git.ci/xihan123/unidbg-qd-sign/image?description=1&forks=1&issues=1&language=1&name=1&owner=1&pulls=1&stargazers=1&theme=Auto)

[![Latest Release](https://img.shields.io/github/release/xihan123/unidbg-qd-sign.svg)](https://github.com/xihan123/unidbg-qd-sign/releases)
[![license](https://img.shields.io/github/license/xihan123/unidbg-qd-sign.svg)](https://www.gnu.org/licenses/gpl-3.0.html)

---

## unidbg-qd-sign

通过 [Unidbg](https://github.com/zhkl0228/unidbg) 获取签名

# 编译方法

- 在根目录下使用 `gradlew buildFatJar` 编译Jar
- 在根目录下使用 `gradlew buildImage` 编译Docker镜像

# 部署方法

## Jar部署

- 需要Java 17+(可自行修改降低版本)
- 默认端口8081(可自行修改端口号)
- 在jar同级目录下创建一个libs文件夹，里面放入文件名为 `com.qidian.QDReader_7.9.333_1186.apk` 的apk

```shell
java -jar unidbg-qd-sign-fat.jar
```

## Docker部署

[xihan123/unidbg-qd-sign](https://hub.docker.com/r/xihan123/unidbg-qd-sign)

- 在docker挂载一个libs文件夹，里面放入文件名为 `com.qidian.QDReader_7.9.333_1186.apk` 的apk
- 以下是示例命令行，不要照抄

```shell
docker run -it --name unidbg-qd-sign -p 8081:8081 -v /home/xihan/unidbg-qd-sign:/libs xihan123/unidbg-qd-sign
```

# 请求示例

- 发起请求

```curl
curl -X GET --location "http://localhost/v1/sign?param=string"
```

- 返回签名

```json
{
  "data": "2f2831a2b75e8a4b9c08ee9a38cadb8c",
  "message": "",
  "result": 0
}
```

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=xihan123/unidbg-qd-sign&type=Date)](https://star-history.com/#xihan123/unidbg-qd-sign&Date)
