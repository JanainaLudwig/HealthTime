# HealthTime

## Release 1

### Requisitos:
 - Java 8
 - PostgreSQL
 
### Instruções de instalação:
IDE utilizada: IntelliJ IDEA

Bibliotecas utilizadas (as biibliotecas estão na pasta _lib_): 
 - jfoenix-8.0.7 (http://www.jfoenix.com/)
 - postgresql-9.4.1209 (https://jdbc.postgresql.org/)
 
DDL e DML estão na pasta _database_

### Questões conhecidas:

**Usabilidade:** salvar a seleção atual do usuário sobre especialidade e médico ao ser canceladas consultas. (Comportamento atual: filtros são reiniciados)

**GUI:** há um bug na renderização do componente JFXComboBox que foge ao escopo do projeto. Será verificado junto ao dev team do framework (se possível, solução para a R2).

### Evolução para Release 2:

Verificar documentação do projeto. _Obs.: Algumas áreas da interface gráfica já possuem boilerplates para as atualizações futuras._
