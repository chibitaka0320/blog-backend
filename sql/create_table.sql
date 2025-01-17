-- # usersテーブル
-- ### 作成
CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL,
	password TEXT NOT NULL,
	introduction VARCHAR(255),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ### 追加
-- (PW:Test01)
INSERT INTO users(name, email, password, introduction)
VALUES('テスト01', 'test01@example.com', '$2a$08$97b3GIIIyRsSOKV4x9PdW.v07Wykoa1q.QXPy/Dc0/9i.95UpKLPK', 'テスト用ユーザー');


-- articlesテーブル
CREATE TABLE articles(
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(2000) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);



-- commentsテーブル
CREATE TABLE comments(
	id SERIAL PRIMARY KEY,
	article_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	content VARCHAR(500) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	FOREIGN KEY (article_id) REFERENCES articles (id) ON DELETE CASCADE
);
