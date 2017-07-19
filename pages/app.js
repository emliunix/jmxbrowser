(function(global){
    var app = global.app = new Vue({
        el: "#app",
        data: {
            domains: [],
            mbeans: []
        },
        methods: {
            showMBeans(d) {
                fetch(`/domain/${d}`)
                    .then(res => res.json())
                    .then(json => app.mbeans = json.result)
            },
            showMBeanInfo(mb) {
                // TODO: impl
            }
        }
    })

    fetch("/domains")
        .then(res => res.json())
        .then(json => {
            app.domains =json.result
        })
})(this)
