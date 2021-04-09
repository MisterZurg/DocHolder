<template>
    <div class="v-table">
        <div class="v-table__header">
            <p>Name</p>
            <p>Description</p>
            <p>Logo</p>
        </div>
        <div class="v-table__body">
            <v-table-row
                v-for="row in paginatedCompanies"
                :key="row.id"
                :row_data="row"
            />
        </div>
        <div class="v-table__pagination">
            <div class="page"
                 v-for="page in pages"
                 :key="page"
                 :class="{'page_selected': page === pageNumber}"
                 @click="pageClick(page)"
            >{{ page }}
            </div>
        </div>
    </div>
</template>

<script>

import vTableRow from './v-table-row'

export default {
    name: "v-table",
    components: {
        vTableRow
    },
    props: {
        companies_data: {
            type: Array,
            default: () => {
                return []
            }
        }
    },
    data() {
        return {
            companiesPerPage: 5,
            pageNumber: 1
        }
    },
    computed: {
        pages() {
            return Math.ceil(this.companies_data.length / 5);
        },
        paginatedCompanies() {
            let from = (this.pageNumber - 1) * this.companiesPerPage;
            let to = from + this.companiesPerPage;
            return this.companies_data.slice(from, to);
        }
    },
    methods: {
        pageClick(page) {
            this.pageNumber = page;
        }
    }
}
</script>

<style scoped>
.v-table {
    max-width: 900px;
    margin: 0 auto;
}

.v-table__header {
    display: flex;
    justify-content: space-around;

}

.v-table__header p {
    flex-basis: 33%;
    text-align: left;
}

.v-table__pagination {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    margin-top: 30px;
    margin-right: 5px;
}

.page {
    padding: 8px;

}

.page:hover {
    background: #42b983;
}

.page_selected {
    background: #2c3e50;
}
</style>