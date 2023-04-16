package com.example
import com.example.dao.MetaDAOImpl
import com.example.dao.ModuleDAOImpl
import com.example.dao.ModuleLinkDAOImpl
import com.example.enums.Status
import com.example.model.Metas
import com.example.model.ModuleLinks
import com.example.model.Modules
import com.opencsv.CSVReader
import kotlinx.coroutines.*
import java.io.File
import java.util.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

@OptIn(DelicateCoroutinesApi::class)
fun loadDataFromFile() {
    GlobalScope.launch {
        cleanTables()
    }

}


@OptIn(DelicateCoroutinesApi::class)
fun fillModules() {
    val modulesFile = File("src/main/resources/filesForFillTables/forModules.csv")
    val reader = CSVReader(modulesFile.reader())
    val dao = ModuleDAOImpl()


    reader.readNext()

    GlobalScope.launch {
        reader.forEach { row ->
            val name = row[0]
            val type = row[1]
            val createdAt = row[2]
            val duration = row[3].toInt()
            val status = Status.valueOf(row[4].uppercase(Locale.getDefault()))
            val description = row[5]
            dao.addNewModule(name, type, createdAt, duration, status, description)
        }
        reader.close()
    }
}
@OptIn(DelicateCoroutinesApi::class)
fun fillMetas() {
    val metasFile = File("src/main/resources/filesForFillTables/forMetas.csv")
    val reader = CSVReader(metasFile.reader())
    val dao = MetaDAOImpl()

    reader.readNext()

    GlobalScope.launch {
        reader.forEach { row ->
            val moduleId = row[0].toInt()
            val metaKey = row[1]
            val metaValue = row[2]
            dao.addNewMeta(moduleId, metaKey, metaValue)
        }
        reader.close()
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun fillModuleLinks() {
    val moduleLinkFile = File("src/main/resources/filesForFillTables/forModuleLinks.csv")
    val reader = CSVReader(moduleLinkFile.reader())
    val dao = ModuleLinkDAOImpl()

    reader.readNext()

    GlobalScope.launch {
        reader.forEach { row ->
            val parentId = row[0].toInt()
            val childId = row[1].toInt()
            val linkType = row[2]
            dao.addNewModuleLink(parentId, childId, linkType)
        }
        reader.close()
    }
}

fun cleanTables() {
    transaction {

//        if(Modules.selectAll().count() > 0) {
//            exec("SET FOREIGN_KEY_CHECKS=0")
//            exec("TRUNCATE TABLE `mydatabase`.`Modules`;")
//            exec("SET FOREIGN_KEY_CHECKS=1")
//        }
//        fillModules()

        if(Metas.selectAll().count() > 0) {
            exec("TRUNCATE TABLE `mydatabase`.`Metas`;")
        }
        fillMetas()
        if(ModuleLinks.selectAll().count() > 0) {
            exec("TRUNCATE TABLE `mydatabase`.`ModuleLinks`;")
        }
        fillModuleLinks()


    }

}