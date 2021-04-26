<template>
    <div class="v-table-row">
        <md-table-cell>{{row_data.name}}</md-table-cell>
        <md-table-cell>{{row_data.description}}</md-table-cell>
        <md-table-cell>{{row_data.logotype}}</md-table-cell>
    </div>
</template>

<script>
export default {
    name: "v-table-row",
    props: {
        row_data:{
            type: Object,
            default: () => {
                return {}
            }
        }
    }
}
</script>

<style>
    .v-table-row {
        display: flex;
        justify-content: space-around;
    }
    .row {
        flex-basis: 33%;
        text-align: left;
    }
</style>