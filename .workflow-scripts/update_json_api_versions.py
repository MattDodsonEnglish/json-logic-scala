import json
import argparse


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('path_json')
    parser.add_argument('api_version')
    parser.add_argument('--scala-versions', dest='scala_versions', nargs='+')
    args = parser.parse_args()
    print(args.path_json)
    print(args.api_version)
    print(args.scala_versions)

