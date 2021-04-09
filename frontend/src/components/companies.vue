<template>
    <div class="main-companies">
        <h1 align="center">OUR COMPANIES</h1>

        <v-table
            :companies_data=this.companies
        />

    </div>
</template>

<script>

document.title = "Companies";

import {mapActions, mapGetters} from 'vuex'
import vTable from './table/v-table'

export default {
    name: "companies",
    data() {
        return {
            companies: createFakeData()
        }
    },
    components: {
        vTable
    },
    computed: {
        ...mapGetters([
            'COMPANIES'
        ])
    },
    methods: {
        ...mapActions([
            'GET_COMPANIES_FROM_API'
        ])
    },
    mounted() {
        this.GET_COMPANIES_FROM_API()
    }
}

function createFakeData() {
    let data = [];
    for (let i = 0; i < 100; i++) {
        data.push({
            id: i,
            name: "Company #" + i,
            description: "bvlah blah",
            logotype: "in dev"
        });
    }
    return data;
}

</script>

<style>

.main-companies {
    margin: 0 auto;
    width: 500px;
    border: 1px solid #333;
}


body {
    background: #fcf8ec;
}

h1 {
    font-family: "Berlin Sans FB";
    color: #456268;
    background: #d0e8f2;
}

h4 {
    font-family: "Berlin Sans FB Demi";
    color: #79a3b1;
    background: #d0e8f2;
}

div {
    color: #79a3b1;
}

</style>

