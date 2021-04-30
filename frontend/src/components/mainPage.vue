<template>
    <div>
        <md-table v-model="searched" md-sort="name" md-sort-order="asc" md-card md-fixed-header>
            <md-table-toolbar>
                <div class="md-toolbar-section-start">
                    <h1 class="md-title">Companies</h1>
                </div>
            </md-table-toolbar>

            <md-table-row slot="md-table-row" slot-scope="{ item }">
                <md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
                <md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
                <md-table-cell md-label="Description" md-sort-by="description">{{ item.description }}</md-table-cell>
            </md-table-row>

        </md-table>
        <div class="v-table__pagination">
            <div class="page"
                 v-for="page in pageColumn"
                 :key="page"
                 :class="{'page_selected': page === pageNumber}"
                 @click="pageClick(page)"
            >{{ page }}
            </div>

            <md-button
                class="md-icon-button"
                :disabled="pageNumber === 1"
                @click="prevPage">
                <md-icon>keyboard_arrow_left</md-icon>
            </md-button>

            <md-button
                class="md-icon-button"
                :disabled="pageNumber >= pageColumn"
                @click="nextPage">
                <md-icon>keyboard_arrow_right</md-icon>
            </md-button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'TableSearch',
    data: () => ({
        search: null,
        searched: [],
        companiesPerPage: 4,
        pageNumber: 1,
        pageColumn: 1,
        companies_data: []

    }),
    mounted: function () {
        this.companies_data = this.getCompanies();
        this.pageColumn = this.getPageColumn();
        this.searched = this.companies_data;
    },
    methods: {
        getCompanies() {
            let data = [];
            this.$http(
                {
                    method: 'get',
                    url: 'http://localhost:8082/company?limit=' + this.companiesPerPage + '&page=' + (this.pageNumber - 1),
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                })
                .then(function (response) {
                    // console.log(response.data.content);
                    let content = response.data.content;
                    for (let i = 0; i < content.length; i++) {
                        data.push({
                            id: i,
                            name: content[i].name,
                            description: content[i].description,
                            logotype: content[i].logo
                        });
                    }
                    return data;
                })
                .catch(function (error) {
                    console.log("catch error : " + error);
                    return error;
                });
            return data;
        },
        getPageColumn() {
            var query = this.$http(
                {
                    method: 'get',
                    url: 'http://localhost:8082/company/count',
                    headers: {
                        "Content-type": "application/json; charset=UTF-8"
                    }
                })
                .then(function (response) {
                    let column = response.data;
                    return Math.ceil(column);
                })
                .catch(function (error) {
                    console.log("catch error : " + error);
                    // return error;
                });
            query.then((response) => {
                // if error happened
                if (response.status != undefined) {
                    console.log(response);
                    let status = response.response.status;
                    if (status == 404) {
                        this.errorText = "Компаний не существует";
                    } else {
                        this.errorText = "Произошла внутренняя ошибка. Попробуйте позже";
                    }
                    return;
                }
                this.pageColumn = Math.ceil(response / this.companiesPerPage);
            });
            return 1;
        },
        pageClick(page) {
            this.pageNumber = page;
            this.companies_data = this.getCompanies();
            this.searched = this.companies_data;
        },
        nextPage() {
            this.pageNumber++;
            this.companies_data = this.getCompanies();
            this.searched = this.companies_data;

        },
        prevPage() {
            this.pageNumber--;
            this.companies_data = this.getCompanies();
            this.searched = this.companies_data;

        }
    },

}
</script>

<style lang="scss" scoped>
.md-table{
    min-height: 550px;
}

.md-table-row {
    height: 100px !important;
}

.md-field {
    max-width: 300px;
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
    cursor: pointer;
}

.page:hover {
    background: #42b983;
}

.page_selected {
    background: #2c3e50;
}

</style>