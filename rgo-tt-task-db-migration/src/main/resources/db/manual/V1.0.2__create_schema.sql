CREATE SCHEMA ${userOwner}
AUTHORIZATION owner_role_${userOwner};

GRANT USAGE
  ON SCHEMA ${userOwner}
         TO ${appRole}, ${readerRole};

ALTER DATABASE ${database}
           SET search_path TO ${userOwner}, public;
