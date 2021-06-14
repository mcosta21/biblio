CREATE SEQUENCE user_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE IF NOT EXISTS "user"
(
    id integer NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    username character varying NOT NULL,
    password character varying NOT NULL,
    name character varying NOT NULL,
    user_type character varying NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)

CREATE SEQUENCE author_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE IF NOT EXISTS "author"
(
    id integer NOT NULL DEFAULT nextval('author_id_seq'::regclass),
    name character varying NOT NULL,
	nationality character varying NOT NULL,
    CONSTRAINT author_pkey PRIMARY KEY (id)
)

CREATE TABLE public.reader
(
    cpf character varying NOT NULL,
    name character varying NOT NULL,
    address character varying,
    PRIMARY KEY (cpf)
);

CREATE TABLE public.teacher
(
    reader_cpf character varying COLLATE pg_catalog."default" NOT NULL,
    discipline character varying COLLATE pg_catalog."default",
    CONSTRAINT teacher_pkey PRIMARY KEY (reader_cpf),
    CONSTRAINT teacher_reader_cpf_fkey FOREIGN KEY (reader_cpf)
        REFERENCES public.reader (cpf) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE public.student
(
    reader_cpf character varying COLLATE pg_catalog."default" NOT NULL,
    registration character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT student_pkey PRIMARY KEY (reader_cpf),
    CONSTRAINT student_reader_cpf_fkey FOREIGN KEY (reader_cpf)
        REFERENCES public.reader (cpf) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE SEQUENCE public.publisher_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE public.publisher
(
    id integer NOT NULL DEFAULT nextval('publisher_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT publisher_pkey PRIMARY KEY (id)
)

CREATE TABLE public.book
(
    isbn character varying NOT NULL,
    name character varying NOT NULL,
    year integer NOT NULL,
    publisher_id integer NOT NULL,
    PRIMARY KEY (isbn),
    FOREIGN KEY (publisher_id)
        REFERENCES public.publisher (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.book_author
(
    book_isbn character varying NOT NULL,
    author_id integer NOT NULL,
    FOREIGN KEY (book_isbn)
        REFERENCES public.book (isbn) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    FOREIGN KEY (author_id)
        REFERENCES public.author (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE public.copybook
(
    id serial NOT NULL,
    book_isbn character varying NOT NULL,
    date_acquisition date,
    status character varying NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (book_isbn)
        REFERENCES public.book (isbn) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);