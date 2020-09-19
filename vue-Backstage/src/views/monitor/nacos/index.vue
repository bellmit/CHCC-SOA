<template>
</template>

<script>
export default {
    data() {
        return {
            nacos: "http://192.161.1.146:8848/nacos"
        }
    },
    created: function() {
        window.open('http://192.161.1.146:8848/nacos','_blank');
    }
}
</script>

<style scoped>
.nacos {
    position: absolute;
}
</style>