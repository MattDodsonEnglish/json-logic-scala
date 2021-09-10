

# shellcheck disable=SC2154
for scala_version in $scala_versions
do
  mkdir -p "$2/scala-$scala_version"
  mv "$1/scala-$scala_version/$api_version" "$2/scala-$scala_version/"
  python "$3/update_json_api_versions.py" "$4" "$api_version" --scala-versions "$scala_versions"
done