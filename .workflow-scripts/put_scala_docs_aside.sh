

scala_versions=$(ls "$1" | grep -Eo "[0-9].[0-9][0-9]" | echo)
api_version=$(sbt version | sed -r 's/[^ \t\r\n\v\f0-9\.](.)/APP_VERSION\1/' | tail -n1 | sed -r 's/^A?P?P?_?V?E?R?S?I?O?N?info\] (.+)/\1/')

(sbt version | sed -r 's/[^ \t\r\n\v\f0-9\.](.)/APP_VERSION\1/')

echo "$scala_versions"
echo "$api_version"

export scala_versions
export api_version

#for scala_version in $scala_versions
#do
#  mkdir -p "$2/scala-$scala_version/$api_version"
#  mv "$1/scala-$scala_version/api/*" "$2/scala-$scala_version/$api_version/"
#done
