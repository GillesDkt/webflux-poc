drop table if exists configuration;

create table configuration
(
    plugin text not null constraint configuration_pkey primary key,
    url    text not null
);

create index index_configuration_plugin
    on configuration (plugin);
