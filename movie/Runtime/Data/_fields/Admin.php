<?php
return array (
  0 => 'admin_id',
  1 => 'admin_name',
  2 => 'admin_pwd',
  3 => 'admin_count',
  4 => 'admin_ok',
  5 => 'admin_del',
  6 => 'admin_ip',
  7 => 'admin_email',
  8 => 'admin_logintime',
  '_autoinc' => true,
  '_pk' => 'admin_id',
  '_type' => 
  array (
    'admin_id' => 'smallint(6) unsigned',
    'admin_name' => 'varchar(50)',
    'admin_pwd' => 'char(32)',
    'admin_count' => 'smallint(6)',
    'admin_ok' => 'varchar(50)',
    'admin_del' => 'bigint(1)',
    'admin_ip' => 'varchar(40)',
    'admin_email' => 'varchar(40)',
    'admin_logintime' => 'int(11)',
  ),
);
?>