```shell
git clone https://github.com/andreblanke/mod-template

mvnw install

mvnw archetype:generate                      \
  -DarchetypeGroupId=dev.andreblanke.mcmods \
  -DarchetypeArtifactId=mod-template-archetype
``` 

```shell
mvnw integration-test
```
