-- Table: tb_stats
CREATE SEQUENCE tb_temperatures_stt_id_seq
    START 1;
CREATE TABLE tb_temperatures
(
    stt_id integer NOT NULL DEFAULT nextval ('tb_temperatures_stt_id_seq' :: regclass),
    stt_temperature character varying(250),
    stt_date timestamp with time zone NOT NULL,
    CONSTRAINT tb_stats_pkey PRIMARY KEY (stt_id)
)
WITH (
    OIDS = FALSE
     );