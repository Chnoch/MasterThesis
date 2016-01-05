/**
 * Created by Chnoch on 07.07.2014.
 */

//def timestampStart = '2013-12-28T22:44:43'
//def date = Date.parse("yyyy-MM-dd'T'HH:mm:ss", timestampStart)
//println date
def m = [ b:1, a:2 ]

// Sort by descending value
m = m.sort { -it.value }

println m // prints [a:2, b:1]
println m.iterator()[0].value
println m[1]