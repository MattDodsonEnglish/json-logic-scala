

scala_versions=$(ls "$1" | grep -Eo "[0-9]\.[0-9][0-9]")
#api_version=$(sbt version | tail -n1 | sed -r 's/[^ \t\r\n\v\f0-9\.] (.+)/APP_VERSION\1/' | sed -r 's/^A?P?P?_?V?E?R?S?I?O?N?info\] (.+)/\1/')
api_version=$(sbt version | tail -n1 | sed -r 's/[^ \t\r\n\v\f0-9\.]+ (.+)/\1/')

(sbt version | tail -n1)
(sbt version | sed -r 's/[^ \t\r\n\v\f0-9\.i?n?f?o?]+ (.+)/APP_VERSION\1/')
(sbt version | sed -r 's/[^ \t\r\n\v\f0-9\.i?n?f?o?]+ (.+)/APP_VERSION\1/' | grep -Eo "^\[?i?n?f?o?A?P?P?_?V?E?R?S?I?O?N?\]? ?(.+)")
(sbt version | sed -r 's/[^ \t\r\n\v\f0-9\.i?n?f?o?]+ (.+)/APP_VERSION\1/' | grep -Eo "^\[?i?n?f?o?A?P?P?_?V?E?R?S?I?O?N?\]? ?(.+)" | sed -r 's/^\[?i?n?f?o?A?P?P?_?V?E?R?S?I?O?N?\]? ?(.+)/\1/')

(sbt version | sed -r 's/[^ \t\r\n\v\f0-9\.i?n?f?o?]+ (.+)/APP_VERSION\1/' | sed -r 's/^\[?i?n?f?o?A?P?P?_?V?E?R?S?I?O?N?\]? ?(.+)/\1/' )
(sbt version | sed -r 's/[^ \t\r\n\v\f0-9\.i?n?f?o?]+ (.+)/\1/')
(sbt version | sed -r 's/[^ \t\r\n\v\f0-9\.]+ (.+)/APP_VERSION\1/' | sed -r 's/^A?P?P?_?V?E?R?S?I?O?N?info\] (.+)/\1/' | grep -nE '*')

echo "$scala_versions"
echo "$api_version"

export scala_versions
export api_version

#for scala_version in $scala_versions
#do
#  mkdir -p "$2/scala-$scala_version/$api_version"
#  mv "$1/scala-$scala_version/api/*" "$2/scala-$scala_version/$api_version/"
#done
