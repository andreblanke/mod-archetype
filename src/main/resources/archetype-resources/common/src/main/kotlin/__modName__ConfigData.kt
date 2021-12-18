package ${package}

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = ${modName}.ID)
class ${modName}ConfigData : ConfigData {

    private var enabled: Boolean = true
}
