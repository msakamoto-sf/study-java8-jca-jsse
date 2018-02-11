# study-java8-jca-jsse
Java8における JCA, JCE の勉強と練習

TODO:
- ※各種練習で、プロバイダの一覧取得などもしてみる。
- 対称鍵暗号（ブロック暗号） : Cipher
- 対象鍵暗号（ストリーム暗号） : CipherStream
- Password-based Encryption (PEB)
- 公開鍵暗号
- 楕円曲線暗号(ECC)
- 鍵ジェネレータ : Key, KeyPair, KeySpec, KeyFactory, SecretKeyFactory, KeyPairGenerator, KeyGenerator
- メッセージ認証コード(MAC) : Mac
- デジタル署名アルゴリズム : Signature
- 鍵合意アルゴリズム : KeyAgreement
- 証明書 : CertificateFactory
- KeyStore(鍵ストレージ) : JKS
- 証明書ストア, 証明書チェーンの検証
- SSL/TLSの検査ツール

## 開発環境

* JDK >= 1.8.0_151 (JDK9は未検証)
  * `lib/security/java.security` にて `crypto.policy = unlimited` が利用できるようになる [8u151](http://www.oracle.com/technetwork/java/javase/8u151-relnotes-3850493.html) 以上が必要です。
* Eclipse >= 4.5.2 (Mars.2 Release), "Eclipse IDE for Java EE Developers" パッケージを使用
* Maven >= 3.5.2 (maven-wrapperにて自動的にDLしてくれます。pom.xml自体は Eclpse 4.5.2 m2e のデフォルト組み込みバージョン : 3.3.3 でも問題なくビルドできます。)
* ソースコードやテキストファイル全般の文字コードはUTF-8を使用

## ビルドと実行

```
cd study-java8-jca-jsse/

ビルド:
mvnw package

jarファイルから実行:
java -jar target/study-java8-jca-jsse-(version).jar

非推奨となった古い鍵長・アルゴリズム・プロトコル含め、可能な限り利用できるようにした設定ファイルで起動:
java -Djava.security.properties=bogus-java-security.properties target/study-java8-jca-jsse-(version).jar

Mavenプロジェクトから直接実行:
mvnw exec:java
```

## Eclipseプロジェクト用の設定

https://github.com/msakamoto-sf/howto-eclipse-setup の `setup-type1` を使用。README.mdで以下を参照のこと:

* Ecipseのインストール
* Clean Up/Formatter 設定
* 必須プラグイン TestNG のインストール
* GitでcloneしたMavenプロジェクトのインポート 


