CREATE TABLE public.bank (
    bank_id uuid NOT NULL,
    bank_created_at timestamp without time zone,
    domiciliation character varying(255),
    bank_iban character varying(255),
    is_active boolean,
    name character varying(255),
    bank_bic character varying(255),
    bank_updated_at timestamp without time zone,
    holder_fk uuid
);


ALTER TABLE public.bank OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 94305)
-- Name: connections; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.connections (
    connection_id uuid NOT NULL,
    is_active boolean,
    friend_fk uuid,
    holder_fk uuid
);


ALTER TABLE public.connections OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 94308)
-- Name: holder; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.holder (
    holder_id uuid NOT NULL,
    created_at timestamp without time zone,
    email character varying(80),
    is_active boolean,
    password character varying(255),
    updated_at timestamp without time zone
);


ALTER TABLE public.holder OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 94311)
-- Name: movement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movement (
    movement_id uuid NOT NULL,
    amount double precision,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    bank_fk uuid,
    holder_fk uuid,
    transaction uuid
);


ALTER TABLE public.movement OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 94314)
-- Name: profiles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.profiles (
    profiles_id uuid NOT NULL,
    address character varying(255),
    created_at timestamp without time zone,
    first_name character varying(255),
    last_name character varying(255),
    phone character varying(255),
    updated_at timestamp without time zone,
    holder_fk uuid NOT NULL
);


ALTER TABLE public.profiles OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 94320)
-- Name: transactions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transactions (
    transaction_id uuid NOT NULL,
    amount double precision,
    created_at timestamp without time zone,
    description character varying(255),
    fees double precision,
    updated_at timestamp without time zone,
    connection_fk uuid
);


ALTER TABLE public.transactions OWNER TO postgres;

--
-- TOC entry 2853 (class 2606 OID 94324)
-- Name: bank bank_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bank
    ADD CONSTRAINT bank_pkey PRIMARY KEY (bank_id);


--
-- TOC entry 2855 (class 2606 OID 94326)
-- Name: connections connections_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.connections
    ADD CONSTRAINT connections_pkey PRIMARY KEY (connection_id);


--
-- TOC entry 2858 (class 2606 OID 94328)
-- Name: holder holder_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.holder
    ADD CONSTRAINT holder_pkey PRIMARY KEY (holder_id);


--
-- TOC entry 2862 (class 2606 OID 94330)
-- Name: movement movement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movement
    ADD CONSTRAINT movement_pkey PRIMARY KEY (movement_id);


--
-- TOC entry 2864 (class 2606 OID 94332)
-- Name: profiles profiles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (profiles_id);


--
-- TOC entry 2868 (class 2606 OID 94334)
-- Name: transactions transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (transaction_id);


--
-- TOC entry 2860 (class 2606 OID 94336)
-- Name: holder uk9f5cm3ndit7bsmrn2bcwtkxcj; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.holder
    ADD CONSTRAINT uk9f5cm3ndit7bsmrn2bcwtkxcj UNIQUE (email);


--
-- TOC entry 2866 (class 2606 OID 94338)
-- Name: profiles uk_8a05ud1yk4iqorpbiyrcm4l6d; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profiles
    ADD CONSTRAINT uk_8a05ud1yk4iqorpbiyrcm4l6d UNIQUE (holder_fk);


--
-- TOC entry 2856 (class 1259 OID 94339)
-- Name: email_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX email_index ON public.holder USING btree (email);


--
-- TOC entry 2872 (class 2606 OID 94340)
-- Name: movement bank_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movement
    ADD CONSTRAINT bank_fk FOREIGN KEY (bank_fk) REFERENCES public.bank(bank_id);


--
-- TOC entry 2876 (class 2606 OID 94345)
-- Name: transactions connection_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT connection_fk FOREIGN KEY (connection_fk) REFERENCES public.connections(connection_id);


--
-- TOC entry 2870 (class 2606 OID 94350)
-- Name: connections friend_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.connections
    ADD CONSTRAINT friend_fk FOREIGN KEY (friend_fk) REFERENCES public.holder(holder_id);


--
-- TOC entry 2869 (class 2606 OID 94355)
-- Name: bank holder_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bank
    ADD CONSTRAINT holder_fk FOREIGN KEY (holder_fk) REFERENCES public.holder(holder_id);


--
-- TOC entry 2871 (class 2606 OID 94360)
-- Name: connections holder_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.connections
    ADD CONSTRAINT holder_fk FOREIGN KEY (holder_fk) REFERENCES public.holder(holder_id);


--
-- TOC entry 2873 (class 2606 OID 94365)
-- Name: movement holder_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movement
    ADD CONSTRAINT holder_fk FOREIGN KEY (holder_fk) REFERENCES public.holder(holder_id);


--
-- TOC entry 2875 (class 2606 OID 94370)
-- Name: profiles holder_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.profiles
    ADD CONSTRAINT holder_fk FOREIGN KEY (holder_fk) REFERENCES public.holder(holder_id);


--
-- TOC entry 2874 (class 2606 OID 94375)
-- Name: movement transaction_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movement
    ADD CONSTRAINT transaction_fk FOREIGN KEY (transaction) REFERENCES public.transactions(transaction_id);


-- Completed on 2020-11-30 17:28:56 CET

--
-- PostgreSQL database dump complete
--

