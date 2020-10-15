<template>
</template>

<script>
import { nacos_path } from '@/api/api'

export default {
    data() {
        return {
            nacos: nacos_path
        }
    },
    created: function() {
        window.open(this.nacos,'_blank');
    }
}
</script>

<style scoped>
.nacos {
    position: absolute;
}
</style>