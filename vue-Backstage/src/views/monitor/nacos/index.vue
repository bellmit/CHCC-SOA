<template>
</template>

<script>
import nacos_path from '@/api/api'

export default {
    data() {
        return {
            nacos: "http://192.161.1.146:8848/nacos"
        }
    },
    created: function() {
        window.open(nacos_path,'_blank');
    }
}
</script>

<style scoped>
.nacos {
    position: absolute;
}
</style>