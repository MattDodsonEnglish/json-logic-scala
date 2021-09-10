

# shellcheck disable=SC2012
scala_versions=$(ls "$1" | ascii2uni -a U -q | grep -Eo "[0-9]\.[0-9][0-9]")
api_version=$(sbt version | ascii2uni -a U -q | tail -n2 | head -n1 | sed 's/[info] //')

export scala_versions
export api_version

for scala_version in $scala_versions
do
  mkdir -p "$2/scala-$scala_version/$api_version"
  mv "$1/scala-$scala_version/api/" "$2/scala-$scala_version/$api_version/"
done
