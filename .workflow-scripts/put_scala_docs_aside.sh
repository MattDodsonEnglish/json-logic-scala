

scala_versions=$(ls "$1" | grep -Eo "[0-9]\.[0-9][0-9]")
api_version=$(sbt version | sed -r 's/[^ \t\r\n\v\f0-9\.i?n?f?o?]+ (.+)/APP_VERSION\1/' | sed -n '6p' | grep -Po '(?<=PP_VERSION)(.+)')


export scala_versions
export api_version

for scala_version in $scala_versions
do
  mkdir -p "$2/scala-$scala_version/$api_version"
  mv "$1/scala-$scala_version/api/" "$2/scala-$scala_version/$api_version/"
done
