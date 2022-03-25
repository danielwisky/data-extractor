# my-crawler

![](https://img.shields.io/badge/Status-Em%20Desenvolvimento-orange)
![](https://img.shields.io/badge/Autor-Daniel%20Wisky-brightgreen)
![](https://img.shields.io/badge/Language-Java-brightgreen)
![](https://img.shields.io/badge/Framework-Spring%20Boot-brightgreen)
![](https://img.shields.io/badge/Arquitetura-Clean%20Arch-brightgreen)

Generic data extraction system

## Technologies

* [Java](https://www.java.com/pt-BR/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Redis](https://redis.io/)
* [Kafka](https://kafka.apache.org/)
* [MongoDB](https://www.mongodb.com/)
* [Maven](https://maven.apache.org/)

## Configuration example

* https://www.bibliaonline.com.br/

```
db.configs.save({
    "type": "BIBLE",
    "url": "https://www.bibliaonline.com.br",
    "content": {
        "type": "version",
        "query": ".jss2 a[href*=https://www.bibliaonline.com.br]",
        "fields": [{
                "name": "key",
                "converter": "VALUE_FROM_HREF",
                filters: [{
                    "converter": "REPLACE_TO_EMPTY",
                    "parameters": ["https://www.bibliaonline.com.br/", "/index"]
                }],
                "replicate": true
            },
            {
                "name": "name",
                "converter": "VALUE_FROM_TEXT"
            }
        ],
        "children": {
            "type": "book",
            "path": "/#{version_key}/index",
            "query": ".jss3 a[href*=https://www.bibliaonline.com.br]",
            "fields": [{
                    "name": "key",
                    "converter": "VALUE_FROM_HREF",
                    filters: [{
                        "converter": "SUBSTRING_AFTER_LAST",
                        "parameters": ["/"]
                    }],
                    "replicate": true
                },
                {
                    "name": "name",
                    "converter": "VALUE_FROM_TEXT"
                }
            ],
            "children": {
                "type": "chapter",
                "path": "/#{version_key}/#{book_key}",
                "query": ".jss1 a[href*=https://www.bibliaonline.com.br]",
                "fields": [{
                        "name": "key",
                        "converter": "VALUE_FROM_HREF",
                        filters: [{
                            "converter": "SUBSTRING_AFTER_LAST",
                            "parameters": ["/"]
                        }],
                        "replicate": true
                    },
                    {
                        "name": "name",
                        "converter": "VALUE_FROM_TEXT"
                    }
                ],
                "children": {
                    "type": "versicle",
                    "path": "/#{version_key}/#{book_key}/#{chapter_key}",
                    "query": "p.jss43",
                    "fields": [{
                            "name": "key",
                            "converter": "VALUE_FROM_TEXT",
                            filters: [{
                                "converter": "SUBSTRING_BEFORE",
                                "parameters": [" "]
                            }]
                        },
                        {
                            "name": "name",
                            "converter": "VALUE_FROM_TEXT",
                            filters: [{
                                "converter": "SUBSTRING_AFTER",
                                "parameters": [" "]
                            }]
                        }
                    ]
                }
            }
        }
    },
    "createdDate": ISODate("2022-03-18T15:26:43.397-03:00"),
    "lastModifiedDate": ISODate("2022-03-18T15:26:43.397-03:00"),
    "_class": "br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents.ConfigurationDocument"
});
```

* https://biblia-a-mensagem.vercel.app/

```
db.configs.save({
    "type": "BIBLE",
    "url": "https://biblia-a-mensagem.vercel.app",
    "content": {
        "type": "book",
        "query": ".MuiButton-label",
        "fields": [{
                "name": "key",
                "converter": "VALUE_FROM_TEXT",
                "filters": [{
                        "converter": "REPLACE_TO_EMPTY",
                        "parameters": [" "]
                    },
                    {
                        "converter": "LOWERCASE",
                        "parameters": [" "]
                    },
                    {
                        "converter": "NORMALIZE"
                    }
                ],
                "replicate": true
            },
            {
                "name": "name",
                "converter": "VALUE_FROM_TEXT"
            }
        ],
        "children": {
            "type": "chapter",
            "path": "/livro/#{book_key}",
            "query": ".MuiButton-label",
            "fields": [{
                    "name": "key",
                    "converter": "VALUE_FROM_TEXT",
                    "replicate": true
                },
                {
                    "name": "name",
                    "converter": "VALUE_FROM_TEXT"
                }
            ],
            "children": {
                "type": "versicle",
                "path": "/livro/#{book_key}/#{chapter_key}",
                "query": "p.MuiTypography-root",
                "fields": [{
                        "name": "key",
                        "converter": "VALUE_FROM_TEXT",
                        filters: [{
                            "converter": "SPLIT_GET_FIRST",
                            "parameters": ["[^\\d\\-]"]
                        }]
                    },
                    {
                        "name": "name",
                        "converter": "VALUE_FROM_OWN_TEXT"
                    }
                ]
            }
        }
    },
    "createdDate": ISODate("2022-03-18T15:26:43.397-03:00"),
    "lastModifiedDate": ISODate("2022-03-18T15:26:43.397-03:00"),
    "_class": "br.com.danielwisky.mycrawler.gateways.outputs.mongodb.documents.ConfigurationDocument"
});
```
