CREATE OR REPLACE FUNCTION random_n(integer) RETURNS integer as
$$
DECLARE ran1 float8;
BEGIN
   ran1 = random() * $1 +1 ;
   RETURN trunc(ran1);
END
$$  language plpgsql;

