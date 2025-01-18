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


-- # articlesテーブル
-- ### 作成
CREATE TABLE articles(
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(2000) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

--- ### 追加
INSERT INTO articles (user_id, title, content, created_at, updated_at)
VALUES
(1, '初めての記事', 'これは私の最初の記事です。プログラミングやウェブ開発に関する興味深いトピックを取り上げています。', '2025-01-18 10:00:00', '2025-01-18 10:00:00'),
(1, 'SQLの学び方', 'SQLはデータベースを管理するための強力な言語です。この記事では、基本的な使い方から応用的なクエリまで解説します。', '2025-01-18 10:15:00', '2025-01-18 10:15:00'),
(1, '機械学習の理解', '機械学習は多くの産業に革新をもたらしています。この記事では、機械学習の仕組みや応用例について説明します。', '2025-01-18 10:30:00', '2025-01-18 10:30:00'),
(1, '2025年のウェブ開発トレンド', 'この記事では、2025年のウェブ開発における最新のトレンド、使用される新しいフレームワークやツールについて紹介します。', '2025-01-18 10:45:00', '2025-01-18 10:45:00'),
(1, 'AIの未来', '人工知能（AI）はさまざまな分野で革新を起こしています。この記事では、AIの潜在能力と将来の展望について考察します。', '2025-01-18 11:00:00', '2025-01-18 11:00:00');




-- # commentsテーブル
-- ### 作成
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

--- ### 追加
INSERT INTO comments (article_id, user_id, content)
VALUES
(1, 1, '素晴らしい記事ですね！ウェブ開発に関する内容がとても面白く、参考になりました。'),
(2, 1, 'SQLはとても面白いですね。この記事を読んで、もっと学びたくなりました！'),
(3, 1, '機械学習についての理解が深まりました。この記事のおかげで、さらに学習を進めたくなりました。'),
(4, 1, 'ウェブ開発のトレンドは日々進化していますね！この記事で最新情報を知ることができて良かったです。'),
(5, 1, 'AIの未来について興味があります。この記事を読んで、ますますその可能性にワクワクしています。');
