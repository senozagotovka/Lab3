import java.util.*

sealed class Result<T>
class Success<T>(val data : T) : Result<T>()
class Error<T>(val message  : String = "Unknown error") : Result<T>()

enum class Operation{
    SORT_ASC 
    {
        override fun <T : Comparable<T>> invoke(list: List<T>) : List<T>
        {
            return list.sorted()
        }
    },
    
    SORT_DESC 
    {
        override fun <T : Comparable<T>> invoke(list: List<T>) : List<T>
        {
            return list.sortedDescending()
        }
    },
    
    SHUFFLE 
    {
        override fun <T : Comparable<T>> invoke(list: List<T>) : List<T>
        {
            return list.shuffled()
        }
    };
    
    abstract operator fun <T : Comparable<T>> invoke(list: List<T>): List<T>
}

fun <T : Comparable<T>> List<T>.operate(operation: Operation): Result<List<T>>
    {
        if (this.isEmpty()) return Error("Empty")
        
        when (operation)
        {
            Operation.SORT_ASC -> return Success(Operation.SORT_ASC(this))
            Operation.SORT_DESC -> return Success(Operation.SORT_DESC(this))
            Operation.SHUFFLE -> return Success(Operation.SHUFFLE(this))
    	}
    
}   

fun generateStrings(stringsLenght: Int, length : Int) : List<String>
{
    val allowedChars = ('A'..'Z') + ('a'..'z')
    var answer = mutableListOf<String>()
    for(i in 1..length) answer.add((1..stringsLenght)
            				  .map { allowedChars.random() }
            				  .joinToString(""))
    return answer
}


fun generateIntegers(length : Int) : List<Int>
{
    return (-1000..1000).shuffled().take(length)
}
    
fun <T : Comparable<T>> Result<List<T>>.print(): Unit
{
    if (this is Error) println(this.message)
    	else if (this is Success) println(this.data)
}

fun main() {   
    generateStrings(6,4).operate(Operation.SORT_ASC).print()
    generateStrings(5,8).operate(Operation.SORT_DESC).print()
	generateStrings(3,3).operate(Operation.SHUFFLE).print()
    generateIntegers(5).operate(Operation.SORT_ASC).print()
    generateIntegers(3).operate(Operation.SORT_DESC).print()
    generateIntegers(9).operate(Operation.SHUFFLE).print()
}
