CREATE OR REPLACE FUNCTION array_agg_flatten_state(text[], text)
RETURNS text[] LANGUAGE plpgsql IMMUTABLE AS $$
BEGIN
  IF $1 IS NULL THEN
    RETURN ARRAY[$2];
ELSE
    RETURN array_cat($1, ARRAY[$2]);
END IF;
END;
$$;

CREATE AGGREGATE array_agg_flatten(text) (
  SFUNC = array_agg_flatten_state,
  STYPE = text[]
);


--  Another flatten function
CREATE OR REPLACE FUNCTION flatten_nested_arrays(nested_arrays text[]) RETURNS text[]
LANGUAGE plpgsql AS $$
DECLARE
result text[];
BEGIN
  FOREACH (array_element IN nested_arrays) LOOP
    FOREACH (element IN unnest(string_to_array(array_element, ','))) LOOP
      result := result || element;
    END LOOP;
  END LOOP;
RETURN array_unique(result); -- Remove duplicates if needed
END;
$$;
