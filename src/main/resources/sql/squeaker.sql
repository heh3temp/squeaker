DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS followers CASCADE;
DROP TABLE IF EXISTS post_mentions CASCADE;
DROP TABLE IF EXISTS post_reactions CASCADE;
DROP TABLE IF EXISTS comment_mentions CASCADE;
DROP TABLE IF EXISTS comment_reactions CASCADE;
DROP TABLE IF EXISTS post_hashtags CASCADE;
DROP TABLE IF EXISTS hashtags CASCADE;

commit;


-- ================= Users ==================
CREATE TABLE IF NOT EXISTS users (
    user_id serial PRIMARY KEY,
    nickname VARCHAR(30) UNIQUE NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL,
    password VARCHAR(1024) NOT NULL,
    join_date TIMESTAMP NOT NULL,
    last_activity TIMESTAMP NOT NULL,
    is_active BOOLEAN NOT NULL,
    description VARCHAR(4000),
    avatar VARCHAR(4000)
);


-- ================= Posts ==================
CREATE TABLE IF NOT EXISTS posts (
    post_id serial PRIMARY KEY,
    user_id INT NOT NULL,
    body VARCHAR(4000) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    modification_date TIMESTAMP NOT NULL,
    picture VARCHAR(4000),
    
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE INDEX posts_user_id_IDX ON posts(user_id);


-- ================ Comments ================
CREATE TABLE IF NOT EXISTS  comments (
    comment_id serial PRIMARY KEY,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    body VARCHAR(4000) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    modification_date TIMESTAMP NOT NULL,
    picture VARCHAR(4000),

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id)

);

CREATE INDEX comments_user_id_IDX ON comments(user_id);
CREATE INDEX comments_post_id_IDX ON comments(post_id);


-- =============== Followers ================
CREATE TABLE IF NOT EXISTS followers (
    follower_id INT NOT NULL,
    followed_id INT NOT NULL,

    PRIMARY KEY (follower_id, followed_id),
    FOREIGN KEY (follower_id) REFERENCES users(user_id),
    FOREIGN KEY (followed_id) REFERENCES users(user_id)
);

CREATE INDEX followers_followed_id_IDX ON followers(followed_id);


-- ============= Post Mentions===============
CREATE TABLE IF NOT EXISTS post_mentions (
    user_id INT NOT NULL,
    post_id INT NOT NULL,

    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

CREATE INDEX post_mentions_post_id_IDX ON post_mentions(post_id);


-- ============ Post Reactions ==============
CREATE TABLE IF NOT EXISTS post_reactions (
    user_id INT NOT NULL,
    post_id INT NOT NULL,

    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

CREATE INDEX post_reactions_post_id_IDX ON post_reactions(post_id);


-- =========== Comment Reactions ============
CREATE TABLE IF NOT EXISTS comment_reactions (
    comment_id INT NOT NULL,
    reaction_author_id INT NOT NULL,

    PRIMARY KEY (comment_id, reaction_author_id),
    FOREIGN KEY (comment_id) REFERENCES comments(comment_id),
    FOREIGN KEY (reaction_author_id) REFERENCES users(user_id)
);

CREATE INDEX comment_reactions_reaction_author_id_IDX ON comment_reactions(reaction_author_id);


-- =========== Comment Mentions =============
CREATE TABLE IF NOT EXISTS comment_mentions (
    comment_id INT NOT NULL,
    mentioned_user_id INT NOT NULL,

    PRIMARY KEY (comment_id, mentioned_user_id),
    FOREIGN KEY (comment_id) REFERENCES comments(comment_id),
    FOREIGN KEY (mentioned_user_id) REFERENCES users(user_id)
);

CREATE INDEX comment_reactions_mentioned_user_id_IDX ON comment_mentions(mentioned_user_id);


-- =============== Hashtags =================
CREATE TABLE IF NOT EXISTS hashtags (
    name VARCHAR(20) PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL
);


-- ============= Post hashtags ==============
CREATE TABLE IF NOT EXISTS post_hashtags (
    name VARCHAR(20) NOT NULL,
    post_id INT NOT NULL,

    PRIMARY KEY (name, post_id),
    FOREIGN KEY (name) REFERENCES hashtags(name),
    FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

CREATE INDEX post_hashtags_post_id_IDX ON post_hashtags(post_id);

commit;