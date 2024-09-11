
$ID = $args[0]
write-host $ID 

$Dir = $PSScriptRoot + '\bin'

$result = $ID + '@10.20.30.1:/home/' + $ID 

scp -r $Dir $result