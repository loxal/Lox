/*
 * Copyright 2010 Alexander Orlov <alexander.orlov@loxal.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package loxal.lox.service.linguistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Alexander Orlov <alexander.orlov@loxal.net>
 */
public final class DomainLangExtractor {

    private final static String encoding = "UTF-8";


    public static void main(final String... args) {
        extractDomainLangTerminology(args);
        extractDomainLangTerminologyFromOriginalList(args);
        getUnknownTerms(args);
    }

    private static void getUnknownTerms(final String... args) {
        final ArrayList<String> langTerms = new ArrayList<String>();
        final Scanner langTermScanner;
        final File originalWordSrc = new File(args[0]);

        try {
            langTermScanner = new Scanner(originalWordSrc, encoding);
            while (langTermScanner.hasNextLine()) {
                langTerms.add(langTermScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (final String langTerm : langTerms) {
            final String prefixRegexLiteral = "(\\d*\\s)\\{(\\w*)\\}.*(\\s\\{S\\}$)";
            final Pattern prefixRegex = Pattern.compile(prefixRegexLiteral);
            final Matcher matcher = prefixRegex.matcher(langTerm);
            final boolean matchFound = matcher.find();
            if (matchFound) {
                System.out.println(matcher.group(2));
            }
        }

    }

    private static void extractDomainLangTerminologyFromOriginalList(final String... args) {
        final ArrayList<String> langTerms = new ArrayList<String>();
        final Scanner langTermScanner;
        final File originalWordSrc = new File(args[0]);
        try {
            langTermScanner = new Scanner(originalWordSrc, encoding);

            while (langTermScanner.hasNextLine()) {
                langTerms.add(langTermScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        final String domainLangTermMarker = "+MED";

        final ArrayList<String> domainLangPrefixList = new ArrayList<String>();
        final Scanner domainLangPrefixListScanner;
        final File prefixList = new File(args[3]);
        try {
            domainLangPrefixListScanner = new Scanner(prefixList, encoding);

            while (domainLangPrefixListScanner.hasNextLine()) {
                domainLangPrefixList.add(domainLangPrefixListScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final ArrayList<String> domainLangKeywordList = new ArrayList<String>();
        final Scanner domainLangKeywordListScanner;
        final File keywordList = new File(args[2]);
        try {
            domainLangKeywordListScanner = new Scanner(keywordList, encoding);

            while (domainLangKeywordListScanner.hasNextLine()) {
                domainLangKeywordList.add(domainLangKeywordListScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final ArrayList<String> domainLangSuffixList = new ArrayList<String>();
        final Scanner domainLangSuffixListScanner;
        final File suffixList = new File(args[4]);
        try {
            domainLangSuffixListScanner = new Scanner(suffixList, encoding);

            while (domainLangSuffixListScanner.hasNextLine()) {
                domainLangSuffixList.add(domainLangSuffixListScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (final String langTerm : langTerms) {
            for (final String domainLangPrefixLiteral : domainLangPrefixList) {
                // TODO could match in the middle of a word "prefix" and not (as expected) also compound words
                final String prefixRegexLiteral = "(\\d*\\s)(.?" + domainLangPrefixLiteral + ".*)(\\+" + domainLangTermMarker + ")?(\\s\\{S\\}$)";
                final Pattern prefixRegex = Pattern.compile(prefixRegexLiteral);
                final Matcher matcher = prefixRegex.matcher(langTerm);
                final boolean matchFound = matcher.find();
                if (matchFound) {
                    System.out.println(matcher.group(2).replace("{", "").replace("}", "") + "," + domainLangTermMarker);
                }
            }


            for (final String domainLangKeywordLiteral : domainLangKeywordList) {
                final String keywordRegexLiteral = "(\\d*\\s)(.?" + domainLangKeywordLiteral + ".*)(\\+" + domainLangTermMarker + ")?(\\s\\{S\\}$)";
                final Pattern keywordRegex = Pattern.compile(keywordRegexLiteral);
                final Matcher matcher = keywordRegex.matcher(langTerm);
                final boolean matchFound = matcher.find();
                if (matchFound) {
                    System.out.println(matcher.group(2).replace("{", "").replace("}", "") + "," + domainLangTermMarker);
                }
            }
//
//
            for (final String domainLangSuffixLiteral : domainLangSuffixList) {
                final String suffixRegexLiteral = "(\\d*\\s)(.?" + domainLangSuffixLiteral + ".*)(\\+" + domainLangTermMarker + ")?(\\s\\{S\\}$)";
                final Pattern keywordRegex = Pattern.compile(suffixRegexLiteral);
                final Matcher matcher = keywordRegex.matcher(langTerm);
                final boolean matchFound = matcher.find();
                if (matchFound) {
                    System.out.println(matcher.group(2).replace("{", "").replace("}", "") + "," + domainLangTermMarker);
                }
            }

        }
    }


    private static void extractDomainLangTerminology(final String... args) {


        final ArrayList<String> langTerms = new ArrayList<String>();
        final Scanner langTermScanner;
        final File unitexRecognizedWords = new File(args[1]);
        try {
            langTermScanner = new Scanner(unitexRecognizedWords, encoding);

            while (langTermScanner.hasNextLine()) {
                langTerms.add(langTermScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        final String domainLangTermMarker = "+MED";

        final ArrayList<String> domainLangPrefixList = new ArrayList<String>();
        final Scanner domainLangPrefixListScanner;
        final File prefixList = new File(args[3]);
        try {
            domainLangPrefixListScanner = new Scanner(prefixList, encoding);

            while (domainLangPrefixListScanner.hasNextLine()) {
                domainLangPrefixList.add(domainLangPrefixListScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final ArrayList<String> domainLangKeywordList = new ArrayList<String>();
        final Scanner domainLangKeywordListScanner;
        final File keywordList = new File(args[2]);
        try {
            domainLangKeywordListScanner = new Scanner(keywordList, encoding);

            while (domainLangKeywordListScanner.hasNextLine()) {
                domainLangKeywordList.add(domainLangKeywordListScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final ArrayList<String> domainLangSuffixList = new ArrayList<String>();
        final Scanner domainLangSuffixListScanner;
        final File suffixList = new File(args[4]);
        try {
            domainLangSuffixListScanner = new Scanner(suffixList, encoding);

            while (domainLangSuffixListScanner.hasNextLine()) {
                domainLangSuffixList.add(domainLangSuffixListScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (final String langTerm : langTerms) {
            for (final String domainLangPrefixLiteral : domainLangPrefixList) {
                // TODO could match in the middle of a word "prefix" and not (as expected) also compound words
                final String prefixRegexLiteral = "(^" + domainLangPrefixLiteral + ".*)(,\\.[A-Z]*)(\\+" + domainLangTermMarker + ")?(.*$)";
                final Pattern prefixRegex = Pattern.compile(prefixRegexLiteral);
                final Matcher matcher = prefixRegex.matcher(langTerm);
                final boolean matchFound = matcher.find();
                if (matchFound) {
                    System.out.println(matcher.group(1) + matcher.group(2) + domainLangTermMarker + matcher.group(4));
                }
            }


            for (final String domainLangKeywordLiteral : domainLangKeywordList) {
                final String keywordRegexLiteral = "(^.*" + domainLangKeywordLiteral + ".*)(,\\.[A-Z]*)(\\+" + domainLangTermMarker + ")?(.*$)";
                final Pattern keywordRegex = Pattern.compile(keywordRegexLiteral);
                final Matcher matcher = keywordRegex.matcher(langTerm);
                final boolean matchFound = matcher.find();
                if (matchFound) {
                    System.out.println(matcher.group(1) + matcher.group(2) + domainLangTermMarker + matcher.group(4));
                }
            }


            for (final String domainLangSuffixLiteral : domainLangSuffixList) {
                final String suffixRegexLiteral = "(^.*" + domainLangSuffixLiteral + ")(,\\.[A-Z]*)(\\+" + domainLangTermMarker + ")?(.*$)";
                final Pattern keywordRegex = Pattern.compile(suffixRegexLiteral);
                final Matcher matcher = keywordRegex.matcher(langTerm);
                final boolean matchFound = matcher.find();
                if (matchFound) {
                    System.out.println(matcher.group(1) + matcher.group(2) + domainLangTermMarker + matcher.group(4));
                }
            }

        }
    }
}