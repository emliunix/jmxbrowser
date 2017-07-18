(ns app.jmx
  (:import [javax.management
            MBeanServerConnection
            MBeanInfo
            ObjectName
            NotificationListener
            Notification]
           [javax.management.remote
            JMXServiceURL
            JMXConnector
            JMXConnectorFactory]))

(def ^:private conn (atom nil))

(defn get-conn []
  (if-let [c @conn]
    c
    ;; else
    (reset! conn
            (fn [_]
              (let [jmx-url-str "service:jmx:rmi:///jndi/rmi://localhost:1314/jmxrmi"
                    jmx-url (JMXServiceURL. jmx-url-str)
                    mxc (JMXConnectorFactory/connect jmx-url)
                    mbsc (.getMBeanServerConnection mxc)]
                mbsc)))))

(def ^:private listener
  (reify
    NotificationListener
    (handleNotification [_ notif handback]
      (println (.getMessage notif)))))

(defn install-listener [conn on]
  (.addNotificationListener conn on listener))

(defn uninstall-listener [conn on]
  (.removeNotificationListener conn on listener))

(defn get-domains []
  (.getDomains (get-conn)))
