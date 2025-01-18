## 概要
自己学習用の記事投稿アプリケーションです。  
本リポジトリはバックエンド用になります。  
共同開発を行うにあたり個人学習用で作成をしているためバグは多々存在します。  
こちらのアプリケーションを起動後、[フロントエンド側](https://github.com/chibitaka0320/blog-frontend)を起動してください。

## バージョン
* java：17
* spring boot：3.4.1
* postgresql：16

## 環境構築
```
git clone git@github.com:chibitaka0320/blog-backend.git
```

## 起動
dockerを使用して、 postgresql の構築及び spring boot の起動を行います。  
すでにjavaやpostgresqlの環境がある人は、sqlフォルダのsqlを実行して起動すれば問題ありません。  
また、以下の実行は [docker](https://docs.docker.com/get-started/get-docker/) をインストールしていることが前提です。  

### ディレクトリの移動
```
cd blog-backend/
```

### 初回実行時
```
docker compose up -d
```
-dをつけることでバックグラウンド実行できる  

### 停止時
```
docker compose stop
```

### 再開時
```
docker compose start
```
