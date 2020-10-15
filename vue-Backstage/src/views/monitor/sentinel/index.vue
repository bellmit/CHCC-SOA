<template></template>
<script>
import sentinel_path from '@/api/api'

export default {
    data() {
        return {
            nacos: "http://192.161.1.146:8888/nacos"
        }
    },
    created: function() {
        window.open(sentinel_path,'_blank');
    }
}
</script>