<template>
    <div>
        <md-table-container>
            <md-table v-model="searched" md-sort="name" md-sort-order="asc" md-card md-fixed-header>
                <md-table-toolbar>
                    <md-field md-clearable class="md-toolbar-section-end">
                        <md-input placeholder="Search by name..." v-model="search" @input="searchOnTable" />
                    </md-field>
                </md-table-toolbar>

                <md-table-empty-state
                    md-label="No companies found"
                    :md-description="`No companies found for this '${search}' query. Try a different search term or create a new company.`">
                    <!-- TODO Add router link -->
                    <md-button class="md-primary md-raised" @click="newUser">Create New Сompany</md-button>
                </md-table-empty-state>

                <md-table-row slot="md-table-row" slot-scope="{ item }">
                    <md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
                    <md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
                    <md-table-cell md-label="Description" md-sort-by="title">{{ item.description }}</md-table-cell>
                </md-table-row>
            </md-table>
            <md-table-pagination class="table-pagination" md-limit=10 md-page=1 md-total=30>

            </md-table-pagination>

        </md-table-container>
    </div>
</template>

<script>
const toLower = text => {
    return text.toString().toLowerCase()
}

const searchByName = (items, term) => {
    if (term) {
        return items.filter(item => toLower(item.name).includes(toLower(term)))
    }

    return items
}

export default {
    name: 'TableSearch',
    data: () => ({
        search: null,
        searched: [],
        users: createFakeData()
    }),
    methods: {
        newUser () {
            window.alert('Noop')
        },
        searchOnTable () {
            this.searched = searchByName(this.users, this.search)
        }
    },
    created () {
        this.searched = this.users
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

<style lang="scss" scoped>
.md-field {
    max-width: 300px;
    // height: 500px;
}

</style>