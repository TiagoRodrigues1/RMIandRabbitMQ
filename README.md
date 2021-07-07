# Sistema de execução de algoritmos, Tabu Search e Genetic Algorithm através de RMI e RabbitMQ

## RMI

Implementadas as interfaces remotas que permitem a comunicação entre cliente e servidor, e também a utilização do Design Pattern Observer, quando JobGroups querem dar um trabalho ao JobGroup

## RabbitMQ

Implementado o Design Pattern Publisher-Subscribe para a passagem de informação do JobGroup para os vários workers, utilizando um Broker FANOUT.

