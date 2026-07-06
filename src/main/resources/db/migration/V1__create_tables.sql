CREATE TABLE tb_associate (
    id BIGSERIAL PRIMARY KEY,
    cpf VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    creation_time timestamptz NOT NULL DEFAULT now()
);
CREATE UNIQUE INDEX uk_associate_cpf ON tb_associate(cpf);

CREATE TABLE tb_agenda (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    creation_time timestamptz NOT NULL DEFAULT now(),
    votes_count_yes BIGINT NOT NULL DEFAULT 0,
    votes_count_no BIGINT NOT NULL DEFAULT 0
);
CREATE INDEX idx_agenda_creation_time ON tb_agenda(creation_time);

CREATE TABLE tb_voting_session (
    id BIGSERIAL PRIMARY KEY,
    agenda_id BIGINT NOT NULL,
    start_time timestamptz NOT NULL DEFAULT now(),
    end_time timestamptz NOT NULL,
    CONSTRAINT fk_voting_session_agenda FOREIGN KEY (agenda_id) REFERENCES tb_agenda(id) ON DELETE CASCADE
);
CREATE INDEX idx_voting_session_agenda ON tb_voting_session(agenda_id);
CREATE INDEX idx_voting_session_dates ON tb_voting_session (start_time, end_time);

CREATE TABLE tb_vote (
    agenda_id BIGINT NOT NULL,
    associate_id BIGINT NOT NULL,
    session_id BIGINT NOT NULL,
    vote_choice VARCHAR(1) NOT NULL,
    voting_time timestamptz NOT NULL DEFAULT now(),
    CONSTRAINT pk_vote PRIMARY KEY (agenda_id, associate_id),
    CONSTRAINT fk_vote_agenda FOREIGN KEY (agenda_id) REFERENCES tb_agenda(id) ON DELETE CASCADE,
    CONSTRAINT fk_vote_associate FOREIGN KEY (associate_id) REFERENCES tb_associate(id) ON DELETE RESTRICT,
    CONSTRAINT chk_vote_choice CHECK (vote_choice IN('Y', 'N'))
);