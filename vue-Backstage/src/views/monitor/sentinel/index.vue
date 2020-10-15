<template></template>
<script>
import { sentinel_path } from '@/api/api'

export default {
    data() {
        return {
            sentinel: sentinel_path
        }
    },
    created: function() {
        window.open(this.sentinel,'_blank');
    }
}
</script>