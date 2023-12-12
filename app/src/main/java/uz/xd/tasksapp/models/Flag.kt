package uz.xd.tasksapp.models

class Flag (var number: Int){
    companion object {
        fun load(): List<Flag> {
            val list = ArrayList<Flag>()

            list.add(Flag(1))
            list.add(Flag(2))
            list.add(Flag(3))
            list.add(Flag(4))
            list.add(Flag(5))
            list.add(Flag(6))
            list.add(Flag(7))
            list.add(Flag(8))
            list.add(Flag(9))
            list.add(Flag(10))

            return list
        }
    }
}