schema_migration:
	sh schema_migration -m -clean
run:
	mvn clean install; cd api; mvn jetty:run
