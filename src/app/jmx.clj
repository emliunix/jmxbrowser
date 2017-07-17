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

(defn get-conn []
  (let [jmx-url-str "service:jmx:rmi:///jndi/rmi://localhost:1314/jmxrmi"
        jmx-url (JMXServiceURL. jmx-url-str)
        mxc (JMXConnectorFactory/connect jmx-url)
        mbsc (.getMBeanServerConnection mxc)]
    mbsc))

(def ^:private listener
  (reify
    NotificationListener
    (handleNotification [_ notif handback]
      (println (.getMessage notif)))))

(defn install-listener [conn on]
  (.addNotificationListener conn on listener))

(defn uninstall-listener [conn on]
  (.removeNotificationListener conn on listener))



