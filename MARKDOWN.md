# MARKDOWN

## stack

* bidi
* httpkit
* cheshire
* jmx

## structure

/domains -> list of domains
/domain/{domain} -> list of names
/mbean/{objectname} -> MBeanInfo
/mbean/{objectname}/attr/{attribute}
/mbean/{objectname}/method/{method}

## httpkit server

```
(org.httpkit.server/run-server handler {:port 8080})
;; -> fn that stop server
(stop-server-fn :timeout 1000)
```

## bidi

(bidi.bidi/match-route routes "url")
(bidi.bidi/path-for routes :index)

## cheshire

(cheshire.core/generate-string obj)
