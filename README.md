# learnApp
learnApp


1)открыть терминал в src/main/webapp 
  выполнить команду npm install @angular/cli
  
2) выполнить Update.sql

3)создать конфиг мавен: 
  
  Working directory: указать папку где лежит проект
  command line: -DdbUrl=jdbc:postgresql://localhost5432/eddb -DdbLogin=postgres -DdbPassword=postgres jooq-codegen:generate
  Profilse(Separated with space): codegen
  Запустить, должны сгенериться классы
  
4)Сгенерить данные (СreateTables.main());  
5)Запустить Application.main()
