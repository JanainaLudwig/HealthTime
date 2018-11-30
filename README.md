# HealthTime

## Release 2

### Requisitos:
 - Java 8
 - PostgreSQL

### APIs utilizadas:
 - IBGE (https://servicodados.ibge.gov.br/api/docs)
 - ipstack (https://ipstack.com/)
 - IBM Cloud - Watson Assistant (https://www.ibm.com/cloud/watson-assistant/)
 - Enviarsms.io (https://enviarsms.io/)

### Instruções de instalação:
IDE utilizada: IntelliJ IDEA

Bibliotecas utilizadas (as biibliotecas estão na pasta _lib_): 
 - jfoenix-8.0.7 (http://www.jfoenix.com/)
 - postgresql-9.4.1209 (https://jdbc.postgresql.org/)
 - watson-ibm-6.9.1 (https://github.com/watson-developer-cloud/java-sdk/)
 - _[pasta]_ httpcomponents-client-4.4.1 (http://hc.apache.org/)
 
DDL e DML estão na pasta _database_

### Instruções de uso:
Códigos dos pacientes:
 - 123456
 - 456789

Senha padrão: **123**

### Questões conhecidas:

**Notificações:** não é enviado SMS no agendamento e no cancelamento de consultas, que poderiam servir de histórico para a parcela da população que utilizaria o sistema através dos ATMs _(Automatic Teller Machine)_ nas UBS (Unidade Básica de Saúde). Também não é enviado das antes da consulta, como notificação de lembrete, de forma a não se perder a consulta ou cancela-la à tempo. Para isso seria necessário uma infra-estrutura que permitisse agendamento de tarefas e a extensão da solução em mais componentes.

### Evoluções possíveis (para uma possível Release 3):
 - Login com tempo de espera incremental antes de novas tentativas, em caso de erro nas credenciais
 - Logout com _timing_
 - Prontuário médico
 - Balanço de medicações/equipamentos a serem adquiridos
