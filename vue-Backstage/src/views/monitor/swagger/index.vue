<template></template>
<script>
import { swagger_path } from '@/api/api'

export default {
    data() {
        return {
            swagger: swagger_path
        }
    },
    created: function() {
        window.open(this.swagger,'_blank');
    }
}
</script>